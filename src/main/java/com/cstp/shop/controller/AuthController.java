package com.cstp.shop.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.cstp.shop.model.dto.LoginDto;
import com.cstp.shop.model.dto.SignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cstp.shop.model.ERole;
import com.cstp.shop.model.Role;
import com.cstp.shop.model.User;
import com.cstp.shop.payload.request.LoginRequest;
import com.cstp.shop.payload.request.SignupRequest;
import com.cstp.shop.payload.response.UserInfoResponse;
import com.cstp.shop.payload.response.MessageResponse;
import com.cstp.shop.repository.RoleRepository;
import com.cstp.shop.repository.UserRepository;
import com.cstp.shop.security.jwt.JwtUtils;
import com.cstp.shop.security.services.UserDetailsImpl;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/form/login")
  public ModelAndView formLogin(@Valid @ModelAttribute LoginDto loginDto, HttpServletRequest request, HttpServletResponse response)
  {
    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

//    response.setHeader(HttpHeaders.SET_COOKIE, String.valueOf(jwtCookie));

    WebUtils.setSessionAttribute(request, jwtUtils.getCookieName(), jwtCookie.getValue());

    return new ModelAndView("redirect:/products");
  }

  @PostMapping("/form/signup")
  public ModelAndView formSignup(@Valid @ModelAttribute SignupDto signupForm, BindingResult result, Model model)
  {
//    if (userRepository.existsByUsername(signupForm.getUsername())) {
//      return "Error: Username is already taken!";
//    }
//
//    if (userRepository.existsByEmail(signupForm.getEmail())) {
//      return "Error: Email is already in use!";
//    }
//
//    if (!signupForm.passwordsMatch()) {
//      return "Error: Passwords do not match!";
//    }
    Set<Role> roles = new HashSet<>();
    User user = new User( signupForm.getUsername(),
                          signupForm.getEmail(),
                          encoder.encode(signupForm.getPassword()));

    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(userRole);
    user.setRoles(roles);


    userRepository.save(user);

    return new ModelAndView("redirect:/login");
  }


  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {


    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getId(),
                                   userDetails.getUsername(),
                                   userDetails.getEmail(),
                                   roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    if (!signUpRequest.passwordsMatch()) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Passwords do not match!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
                         signUpRequest.getEmail(),
                         encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "employee":
          Role workerRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(workerRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }
}
