package com.example.annex_founder.dto.get;

import com.example.annex_founder.dto.HouseRenterDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class AnnexDtoGet {
    private Long annexId;
    private String location;
    private Double price;
    private String description;
    private String facilities;
    private String rating;
    private String status;
    private HouseRenterDto renterDto;

}
