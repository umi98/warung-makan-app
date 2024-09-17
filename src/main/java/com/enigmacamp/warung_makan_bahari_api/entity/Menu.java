package com.enigmacamp.warung_makan_bahari_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "m_menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;
    @Positive
    @Column(name = "price", nullable = false)
    private Long price;

    @JsonIgnore
    @OneToMany(mappedBy = "menu", cascade = CascadeType.PERSIST)
    private List<OrderDetail> orderDetail;

    @OneToOne
    @JoinColumn(name = "m_menu_image_id", unique = true)
    private MenuImage menuImage;
}
