package com.example.annex_founder.service.impl;

import com.example.annex_founder.dto.AdminDto;
import com.example.annex_founder.model.Admins;
import com.example.annex_founder.repo.AdminRepo;
import com.example.annex_founder.service.AdminService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepo adminRepo;
    private final ModelMapper modelMapper;

    public AdminServiceImpl(AdminRepo adminRepo, ModelMapper modelMapper) {
        this.adminRepo = adminRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public AdminDto adminLogin(AdminDto dto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<Admins> userNames = adminRepo.findByUserName(dto.getUserName());
        for (Admins name : userNames) {
            boolean isPasswordMatches = passwordEncoder.matches(dto.getPassword(), name.getPassword());
            if (isPasswordMatches) {
                return entityToDto(name);
            }
        }
        return null;
    }

    private AdminDto entityToDto(Admins name) {
        return (name == null) ? null : modelMapper.map(name, AdminDto.class);
    }
}
