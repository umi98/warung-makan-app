package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.constant.ERole;
import com.enigmacamp.warung_makan_bahari_api.dto.request.AdminRegisterRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.AuthRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.CustRegisterRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.LoginResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.RegisterResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.*;
import com.enigmacamp.warung_makan_bahari_api.repository.UserCredentialRepository;
import com.enigmacamp.warung_makan_bahari_api.security.JwtUtil;
import com.enigmacamp.warung_makan_bahari_api.service.AdminService;
import com.enigmacamp.warung_makan_bahari_api.service.AuthService;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import com.enigmacamp.warung_makan_bahari_api.service.RoleService;
import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialRepository userCredentialRepository;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final ValidationUtil validationUtil;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AdminService adminService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse custRegister(CustRegisterRequest request) {
        try {
            validationUtil.validate(request);
            // role
            Role role = roleService.getOrSave(Role.builder()
                    .roleName(ERole.ROLE_CUSTOMER)
                    .build());
            // user credential
            UserCredential newUser = UserCredential.builder()
                    .username(request.getUsername().toLowerCase())
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
//                    .isMember(newCustomer.getIsMember())
                    .phoneNumber(newCustomer.getPhoneNumber())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }

    @Override
    public RegisterResponse adminRegister(AdminRegisterRequest request) {
        try {
            validationUtil.validate(request);
            // role
            Role role = roleService.getOrSave(Role.builder()
                    .roleName(ERole.ROLE_ADMIN)
                    .build());
            // user credential
            UserCredential newUser = UserCredential.builder()
                    .username(request.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(newUser);
            // admin
            Admin newAdmin = Admin.builder()
                    .name(request.getAdminRequest().getFullName())
                    .phoneNumber(request.getAdminRequest().getPhoneNumber())
                    .userCredential(newUser)
                    .build();
            adminService.addAdmin(newAdmin);
            return RegisterResponse.builder()
                    .username(newUser.getUsername())
                    .role(newUser.getRole().getRoleName().name())
                    .fullName(newAdmin.getName())
                    .phoneNumber(newAdmin.getPhoneNumber())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        validationUtil.validate(request);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername().toLowerCase(), request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        AppUser appUser = (AppUser) authenticate.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        return LoginResponse.builder()
                .token(token)
                .id(appUser.getId())
                .username(request.getUsername())
                .role(appUser.getRole().name())
                .build();
    }

}
