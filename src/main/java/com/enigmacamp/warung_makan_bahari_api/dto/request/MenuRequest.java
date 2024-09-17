package com.enigmacamp.warung_makan_bahari_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotNull
    @Positive(message = "require positive value")
    private Long price;
    @NotNull(message = "image requires")
    private MultipartFile multipartFile;
}
