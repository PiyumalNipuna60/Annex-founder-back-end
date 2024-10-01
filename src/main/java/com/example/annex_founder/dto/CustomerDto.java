package com.example.annex_founder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CustomerDto {
    private Long customerId;
    private String userName;
    private String email;
    private String contact;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String location;
    private String profilePic;

}
