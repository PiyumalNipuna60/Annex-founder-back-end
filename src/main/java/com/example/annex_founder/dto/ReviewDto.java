package com.example.annex_founder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ReviewDto {
    private Long reviewId;
    private String description;
    private Long annexId;
}
