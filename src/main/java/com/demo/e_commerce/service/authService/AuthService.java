package com.demo.e_commerce.service.authService;

import com.demo.e_commerce.dto.authDto.JwtResponse;
import com.demo.e_commerce.dto.authDto.LoginRequest;
import com.demo.e_commerce.dto.authDto.SignUpRequest;
import com.demo.e_commerce.dto.commonDto.CommonDto;
import com.demo.e_commerce.enums.ERole;
import com.demo.e_commerce.model.CartEntity;
import com.demo.e_commerce.model.RoleEntity;
import com.demo.e_commerce.model.UserEntity;
import com.demo.e_commerce.repository.rolerepo.RoleRepository;
import com.demo.e_commerce.repository.userrepo.UserRepository;
import com.demo.e_commerce.security.UserDetailsImpl;
import com.demo.e_commerce.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
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


    public ResponseEntity<?> login(LoginRequest loginRequest) {
        // Note: loginRequest.getUsername() can contain either username or email.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwt);
        jwtResponse.setRoles(roles);
        jwtResponse.setUsername(userDetails.getUsername());
        jwtResponse.setEmail(userDetails.getEmail());
        jwtResponse.setId(userDetails.getId());
        return ResponseEntity.ok(jwtResponse);
    }


    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsernameAndIsDeleted(signUpRequest.getUsername(), 0)) {
//            return ResponseEntity.badRequest().body("Error: Username is already taken!");
            CommonDto commonDto = new CommonDto();
            commonDto.setData(null);
            commonDto.setMessage("Username is already in use.");
            commonDto.setSuccess(false);
            return ResponseEntity.badRequest().body(commonDto);
        }


        if (userRepository.existsByEmailAndIsDeleted(signUpRequest.getEmail(), 0)) {
//            return ResponseEntity.badRequest().body("Error: Email is already in use!");
            CommonDto commonDto = new CommonDto();
            commonDto.setData(null);
            commonDto.setMessage("Email is already registered.");
            commonDto.setSuccess(false);
            return ResponseEntity.badRequest().body(commonDto);
        }


        // Create new user's account
        UserEntity user = new UserEntity();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        // Initialize an empty cart for the new user!
        CartEntity cart = new CartEntity();
        cart.setUser(user);
        user.setCart(cart);

        userRepository.save(user);

        CommonDto commonDto = new CommonDto();
        commonDto.setMessage("User registered successfully!");
        commonDto.setSuccess(true);
        return ResponseEntity.ok(commonDto);
    }
}
