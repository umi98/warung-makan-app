package com.enigmacamp.warung_makan_bahari_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private String fullName;
    private String phoneNumber;
    private Boolean isMember;
}
