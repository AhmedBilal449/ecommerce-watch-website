package com.cstp.shop.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.cstp.shop.model.Product;
import com.cstp.shop.model.dto.ProductDto;
import com.cstp.shop.model.dto.SignupDto;
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
                                        productRequest.getStock(),
                                        productRequest.getRating());
        productRepository.save(product);

        return ResponseEntity.ok(new MessageResponse("Product added successfully!"));
    }

    @PostMapping("/form/add")
    public ModelAndView formAddProduct(@Valid @ModelAttribute ProductDto productDto)
    {
        Product product = new Product(  productDto.getName(),
                                        productDto.getDescription(),
                                        productDto.getCategory(),
                                        productDto.getImgpath(),
                                        productDto.getPrice(),
                                        productDto.getStock(),
                                        productDto.getRating());
        productRepository.save(product);
        return new ModelAndView("redirect:/admin/products");
    }

    @PostMapping("/form/remove")
    public ModelAndView formRemoveroduct(@RequestParam Long id)
    {
        Product product = productRepository.findById(id).get();
        if (product != null) productRepository.delete(product);
        return new ModelAndView("redirect:/admin/products");
    }

}
