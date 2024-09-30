package com.example.annex_founder.controller;

import com.example.annex_founder.dto.HouseRenterDto;
import com.example.annex_founder.service.HouseRenterService;
import com.example.annex_founder.util.JWTTokenGenerator;
import com.example.annex_founder.util.TokenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/house_renter")
public class HouseRenterController {

    private final JWTTokenGenerator jwtTokenGenerator;
    private final HouseRenterService houseRenterService;

    @Autowired
    public HouseRenterController(JWTTokenGenerator jwtTokenGenerator, HouseRenterService houseRenterService) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.houseRenterService = houseRenterService;
    }


    @PostMapping
    public ResponseEntity<Object> saveHouseRenter(@RequestBody HouseRenterDto houseRenterDto, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            if (this.houseRenterService.isExitHouseRenter(houseRenterDto)) {
                return new ResponseEntity<>("User Is Exits..!", HttpStatus.BAD_REQUEST);
            } else {
                HouseRenterDto dto = this.houseRenterService.saveHouseRenter(houseRenterDto);
                return new ResponseEntity<>(dto, HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllHouseRenter(@RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            List<HouseRenterDto> dtoList = this.houseRenterService.getAllHouseRenter();
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{renterId}")
    public ResponseEntity<Object> searchHouseRenter(@PathVariable Long renterId, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            HouseRenterDto houseRenterDto = this.houseRenterService.searchHouseRenter(renterId);
            return new ResponseEntity<>(houseRenterDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{renterId}")
    public ResponseEntity<Object> updateHouseRenter(@PathVariable Long renterId, @RequestBody HouseRenterDto houseRenterDto, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            HouseRenterDto dto = this.houseRenterService.updateHouseRenter(renterId, houseRenterDto);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{renterId}")
    public ResponseEntity<Object> deleteHouseRenter(@PathVariable Long renterId, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            HouseRenterDto dto = this.houseRenterService.deleteHouseRenter(renterId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }
}
