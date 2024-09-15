package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.response.AdminResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Admin;

public interface AdminService {
    AdminResponse addAdmin(Admin admin);
}
