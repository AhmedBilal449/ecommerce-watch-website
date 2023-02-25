package com.cstp.shop.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.cstp.shop.model.Product;
import com.cstp.shop.payload.request.ProductRequest;
import com.cstp.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JwtUtils jwtUtils;
    
    @PostMapping("/add")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest)
    {
        Product product = new Product(  productRequest.getName(),
                                        productRequest.getDescription(),
                                        productRequest.getCategory(),
                                        productRequest.getImgpath(),
                                        productRequest.getPrice(),
                                        productRequest.getStock());
        productRepository.save(product);

        return ResponseEntity.ok(new MessageResponse("Product added successfully!"));
    }
}
