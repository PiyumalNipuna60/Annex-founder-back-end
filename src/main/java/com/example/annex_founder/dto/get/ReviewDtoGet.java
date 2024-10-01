package com.example.annex_founder.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ReviewDtoGet {
    private Long reviewId;
    private String description;
    private AnnexDtoGet annex;
}
