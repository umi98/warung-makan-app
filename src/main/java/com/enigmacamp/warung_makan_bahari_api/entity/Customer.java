package com.enigmacamp.warung_makan_bahari_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="m_customer")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "full_name", nullable = false)
    @NotEmpty
    private String fullName;
    @Column(name = "phone_number", nullable = false, unique = true)
    @NotEmpty
    private String phoneNumber;
    @Column(name = "is_member", nullable = false)
    @NotNull
    private Boolean isMember;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<Order> order;

}
