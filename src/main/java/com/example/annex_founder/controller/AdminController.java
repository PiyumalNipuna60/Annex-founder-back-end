package com.example.annex_founder.controller;

import com.example.annex_founder.dto.AdminDto;
import com.example.annex_founder.dto.AdminDtoGet;
import com.example.annex_founder.service.AdminService;
import com.example.annex_founder.util.JWTTokenGenerator;
import com.example.annex_founder.util.TokenStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final JWTTokenGenerator jwtTokenGenerator;

    public AdminController(AdminService adminService, JWTTokenGenerator jwtTokenGenerator) {
        this.adminService = adminService;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }


    @PostMapping("/register")
    public ResponseEntity<Object> registerAdmin(@RequestBody AdminDto userDto, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {

            AdminDtoGet isUser = this.adminService.findAdminByName(userDto.getEmail(), userDto.getUserName());
            if (isUser == null) {
                AdminDtoGet dto = this.adminService.registerAdmin(userDto);
                return new ResponseEntity<>(dto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("User is exist", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
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

    @PutMapping("/update/{adminId}")
    public ResponseEntity<Object> updateAdmin(@PathVariable Long adminId, @RequestBody AdminDto adminDto, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            AdminDtoGet dto = this.adminService.updateAdmin(adminDto, adminId);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/get_all_admin")
    public ResponseEntity<Object> getAllAdmin(@RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            List<AdminDtoGet> allUsers = this.adminService.getAllAdmin();
            return new ResponseEntity<>(allUsers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/get_user_info_by_token")
    public ResponseEntity<Object> getAdminInfoByToken(@RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            AdminDtoGet userFromJwtToken = this.jwtTokenGenerator.getAdminFromJwtToken(authorizationHeader);
            return new ResponseEntity<>(userFromJwtToken, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }
}
