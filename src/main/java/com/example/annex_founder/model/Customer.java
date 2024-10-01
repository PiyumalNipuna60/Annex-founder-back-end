package com.example.annex_founder.model;

import jakarta.persistence.*;
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
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String userName;

    @Column(unique = true)
    private String email;
    private String password;
    private String location;
    private String contact;

    @Column(nullable = true)
    private String profilePic;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Review> reviewList;
}
