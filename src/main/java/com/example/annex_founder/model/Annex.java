package com.example.annex_founder.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "annex")
public class Annex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long annexId;
    private String location;
    private Double price;
    private String description;
    private String facilities;
    private String rating;
    private String status;


    @ManyToOne(optional = false)
    @JoinColumn(name = "houseRenterId")
    private HouseRenter houseRenter;
}
