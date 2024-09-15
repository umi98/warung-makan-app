package com.enigmacamp.warung_makan_bahari_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "m_table")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tables {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "table_name", nullable = false, unique = true)
    @NotEmpty
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "tables", cascade = CascadeType.PERSIST)
    private List<Order> order;
}
