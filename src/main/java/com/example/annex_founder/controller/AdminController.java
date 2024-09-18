package com.example.annex_founder.controller;

import com.example.annex_founder.dto.AdminDto;
import com.example.annex_founder.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    private  final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public Map<String, String> postLogin(@RequestBody AdminDto dto) {
        AdminDto adminDto = adminService.adminLogin(dto);
        Map<String, String> response = new HashMap<>();
        if (adminDto == null) {
            response.put("massage", "wrong details");
        } else {
           // String token = this.jwtTokenGenerator.generateJwtToken(user);
            //response.put("token", token);
        }
        return response;
    }
}
