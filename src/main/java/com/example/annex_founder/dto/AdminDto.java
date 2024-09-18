package com.example.annex_founder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class AdminDto {
    private Long adminId;
    private String email;
    private String userName;
    private String password;
}
