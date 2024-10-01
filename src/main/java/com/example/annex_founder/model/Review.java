package com.example.annex_founder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "annexId")
    private Annex annex;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerId")
   private Customer customer;
}
