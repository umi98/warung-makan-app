package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.request.AdminRegisterRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.AuthRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.CustRegisterRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.LoginResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse custRegister(CustRegisterRequest request);
    RegisterResponse adminRegister(AdminRegisterRequest request);
    LoginResponse login(AuthRequest request);
}
