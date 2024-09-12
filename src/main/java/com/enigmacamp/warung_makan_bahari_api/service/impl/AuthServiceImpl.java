package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.constant.ERole;
import com.enigmacamp.warung_makan_bahari_api.dto.request.AuthRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.LoginResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.RegisterResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.Role;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import com.enigmacamp.warung_makan_bahari_api.repository.UserCredentialRepository;
import com.enigmacamp.warung_makan_bahari_api.security.JwtUtil;
import com.enigmacamp.warung_makan_bahari_api.service.AuthService;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import com.enigmacamp.warung_makan_bahari_api.service.RoleService;
import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialRepository userCredentialRepository;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final ValidationUtil validationUtil;
    private final JwtUtil jwtUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(RegisterRequest request) {
        try {
            validationUtil.validate(request);
            // role
            Role role = roleService.getOrSave(Role.builder()
                    .roleName(ERole.ROLE_CUSTOMER)
                    .build());
            // user credential
            UserCredential newUser = UserCredential.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(newUser);
            // customer
            Customer newCustomer = Customer.builder()
                    .fullName(request.getCustomer().getFullName())
                    .phoneNumber(request.getCustomer().getPhoneNumber())
                    .isMember(request.getCustomer().getIsMember())
                    .userCredential(newUser)
                    .build();
            customerService.addCustomer(newCustomer);
            return RegisterResponse.builder()
                    .username(newUser.getUsername())
                    .role(newUser.getRole().getRoleName().name())
                    .fullName(newCustomer.getFullName())
                    .isMember(newCustomer.getIsMember())
                    .phoneNumber(newCustomer.getPhoneNumber())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }

//    @Override
//    public LoginResponse login(AuthRequest request) {
//        Optional<UserCredential> result = userCredentialRepository.findByUsernameAndPassword(request.getUsername(), passwordEncoder.encode(request.getPassword()));
//        if (result.isPresent()) {
//            String token = jwtUtil.generateToken("id");
//            return LoginResponse.builder()
//                    .username(request.getUsername())
//                    .role(result.get().getRole().getRoleName().name())
//                    .token(token).build();
//        }
//        return LoginResponse.builder()
//                .username(result.toString()).build();
//    }
}
