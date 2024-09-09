package com.enigmacamp.warung_makan_bahari_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigmacamp.warung_makan_bahari_api.entity.Tables;

public interface TablesRepository extends JpaRepository<Tables, String> {
    List<Tables> getByNameLikeAllIgnoreCase(String id);
}
