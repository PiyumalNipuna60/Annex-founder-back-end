package com.example.annex_founder.controller;

import com.example.annex_founder.dto.CustomerDto;
import com.example.annex_founder.service.CustomerService;
import com.example.annex_founder.util.JWTTokenGenerator;
import com.example.annex_founder.util.TokenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final JWTTokenGenerator jwtTokenGenerator;
    private final CustomerService customerService;

    @Autowired
    public CustomerController(JWTTokenGenerator jwtTokenGenerator, CustomerService customerService) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Object> saveHouseCustomer(@RequestBody CustomerDto customerDto, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            if (this.customerService.isExitCustomer(customerDto)) {
                return new ResponseEntity<>("User Is Exits..!", HttpStatus.BAD_REQUEST);
            } else {
                CustomerDto dto = this.customerService.saveCustomer(customerDto);
                return new ResponseEntity<>(dto, HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/login")
    public Map<String, String> customerLogin(@RequestBody CustomerDto dto) {
        CustomerDto customerDto = customerService.customerLogin(dto);
        Map<String, String> response = new HashMap<>();
        if (customerDto == null) {
            response.put("massage", "wrong details");
        } else {
            String token = this.jwtTokenGenerator.generateJwtTokenForCustomer(customerDto);
            response.put("token", token);
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<Object> getAllCustomer(@RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            List<CustomerDto> dtoList = this.customerService.getAllCustomer();
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Object> searchCustomer(@PathVariable Long customerId, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            CustomerDto houseRenterDto = this.customerService.searchCustomer(customerId);
            return new ResponseEntity<>(houseRenterDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDto customerDto, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            CustomerDto dto = this.customerService.updateCustomer(customerId, customerDto);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerId, @RequestHeader(name = "Authorization") String authorizationHeader) {
        if (this.jwtTokenGenerator.validateJwtToken(authorizationHeader)) {
            CustomerDto dto = this.customerService.deleteCustomer(customerId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(TokenStatus.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

}
