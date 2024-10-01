package com.example.annex_founder.util;

import com.example.annex_founder.dto.AdminDto;
import com.example.annex_founder.dto.CustomerDto;
import com.example.annex_founder.dto.get.AdminDtoGet;
import com.example.annex_founder.service.AdminService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenGenerator {
    private static final Logger logger = LoggerFactory.getLogger(JWTTokenGenerator.class);
    private final AdminService adminService;
    @Value("${annexfounder.app.jwtSecret}")
    private String jwtSecret;
    @Value("${annexfounder.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    public JWTTokenGenerator(AdminService adminService) {
        this.adminService = adminService;
    }

    public String generateJwtToken(AdminDto adminDto) {
        return Jwts.builder()
                .setId(String.valueOf(adminDto.getAdminId()))
                .setSubject((adminDto.getUserName()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtTokenForCustomer(CustomerDto customerDto) {
        return Jwts.builder()
                .setId(String.valueOf(customerDto.getCustomerId()))
                .setSubject((customerDto.getUserName()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        String jwtToken = authToken.substring("Bearer ".length());
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(jwtToken);
            return true;
        } catch (Exception e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        return false;
    }

    public AdminDtoGet getAdminFromJwtToken(String token) {
        String jwtToken = token.substring("Bearer ".length());
        String id = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(jwtToken).getBody().getId();
        return adminService.getAdminById(id);
    }
}
