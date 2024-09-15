package com.enigmacamp.warung_makan_bahari_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRequest {
    @NotBlank(message = "name is required")
    private String fullName;
    private Boolean isMember = false;
    @UniqueElements
    @NotBlank(message = "phone number is required")
    private String phoneNumber;
}
