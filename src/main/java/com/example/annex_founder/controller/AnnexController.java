package com.example.annex_founder.controller;

import com.example.annex_founder.dto.AnnexDto;
import com.example.annex_founder.dto.get.AnnexDtoGet;
import com.example.annex_founder.service.AnnexService;
import com.example.annex_founder.util.JWTTokenGenerator;
import com.example.annex_founder.util.TokenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/annex")
public class AnnexController {
    private final AnnexService annexService;
    private final JWTTokenGenerator jwtTokenGenerator;

    @Autowired
    public AnnexController(AnnexService annexService, JWTTokenGenerator jwtTokenGenerator) {
        this.annexService = annexService;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }


    @PostMapping
    public ResponseEntity<Object> saveAnnex(@RequestBody AnnexDto annexDto, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
                AnnexDto dto = this.annexService.saveAnnex(annexDto);
                return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllAnnex(@RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            List<AnnexDtoGet> dtoList = this.annexService.getAllAnnex();
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{annexId}")
    public ResponseEntity<Object> searchAnnex(@PathVariable Long annexId, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            AnnexDtoGet dto = this.annexService.searchAnnex(annexId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{annexId}")
    public ResponseEntity<Object> updateAnnex(@PathVariable Long annexId, @RequestBody AnnexDto annexDto, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            AnnexDto dto = this.annexService.updateAnnex(annexId, annexDto);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{annexId}")
    public ResponseEntity<Object> deleteAnnex(@PathVariable Long annexId, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            AnnexDto dto = this.annexService.deleteAnnex(annexId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }
}
