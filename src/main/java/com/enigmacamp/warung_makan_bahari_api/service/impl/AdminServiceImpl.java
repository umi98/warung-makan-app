package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.constant.ERole;
import com.enigmacamp.warung_makan_bahari_api.dto.request.AdminRegisterRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.AuthRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.CustRegisterRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.AdminResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.LoginResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.RegisterResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Admin;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.Role;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import com.enigmacamp.warung_makan_bahari_api.repository.AdminRepository;
import com.enigmacamp.warung_makan_bahari_api.repository.UserCredentialRepository;
import com.enigmacamp.warung_makan_bahari_api.service.AdminService;
import com.enigmacamp.warung_makan_bahari_api.service.RoleService;
import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final ValidationUtil validationUtil;


    @Override
    public AdminResponse addAdmin(Admin admin) {
        try {
            validationUtil.validate(admin);
            adminRepository.save(admin);
            return mapToResponse(admin);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number has been used by another account");
        }
    }

    private AdminResponse mapToResponse(Admin admin) {
        return AdminResponse.builder()
                .fullName(admin.getName())
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }
}
