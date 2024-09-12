package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.constant.ERole;
import com.enigmacamp.warung_makan_bahari_api.entity.Role;
import com.enigmacamp.warung_makan_bahari_api.repository.RoleRespository;
import com.enigmacamp.warung_makan_bahari_api.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRespository roleRespository;

    @Override
    public Role getOrSave(Role role) {
        Optional<Role> optRole = roleRespository.findByRoleName(role.getRoleName());
        if (!optRole.isEmpty()) {
            return optRole.get();
        }
        return roleRespository.save(role);
    }
}
