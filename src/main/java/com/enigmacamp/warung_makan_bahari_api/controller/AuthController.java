package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.constant.PathApi;
import com.enigmacamp.warung_makan_bahari_api.dto.request.AdminRegisterRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.AuthRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.CustRegisterRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.LoginResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.RegisterResponse;
import com.enigmacamp.warung_makan_bahari_api.service.AuthService;
import com.enigmacamp.warung_makan_bahari_api.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathApi.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ResponseBuilder responseBuilder;

    @PostMapping(PathApi.REGISTER_USER)
    public ResponseEntity<?> register(@RequestBody CustRegisterRequest request) {
        RegisterResponse registerResponse = authService.custRegister(request);
        return responseBuilder.buildResponse(registerResponse, "Registered", HttpStatus.CREATED);
    }

    @PostMapping(PathApi.REGISTER_ADMIN)
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegisterRequest request) {
        RegisterResponse registerResponse = authService.adminRegister(request);
        return responseBuilder.buildResponse(registerResponse, "Registered", HttpStatus.CREATED);
    }

    @PostMapping(PathApi.LOGIN)
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        LoginResponse login = authService.login(request);
        return responseBuilder.buildResponse(login, "Login succeess", HttpStatus.CREATED);
    }
}
