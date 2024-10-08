package com.example.annex_founder.model;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "house_renters")
public class HouseRenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long renterId;
    private String name;
    private String email;
    private String contact;
    private String password;

    @OneToMany(mappedBy = "houseRenter", cascade = CascadeType.ALL)
    private List<Annex> annexList;

    public HouseRenter(Long renterId) {
        this.renterId=renterId;
    }
}
