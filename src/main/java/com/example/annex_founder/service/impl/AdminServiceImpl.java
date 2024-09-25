package com.example.annex_founder.service.impl;

import com.example.annex_founder.dto.AdminDto;
import com.example.annex_founder.dto.AdminDtoGet;
import com.example.annex_founder.model.Admins;
import com.example.annex_founder.repo.AdminRepo;
import com.example.annex_founder.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public AdminDtoGet findAdminByName(String email, String userName) {
        Admins userDetails = adminRepo.findUserByEmailAndUserName(email, userName);
        return entityToAdminDtoGet(userDetails);
    }

    @Override
    public AdminDtoGet registerAdmin(AdminDto userDto) {
        Admins user1 = this.dtoToEntity(userDto);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user1.setPassword(passwordEncoder.encode(user1.getPassword()));
        Admins save = this.adminRepo.save(user1);
        return entityToAdminDtoGet(save);
    }

    @Override
    public AdminDtoGet updateAdmin(AdminDto adminDto, Long adminId) {
        Admins byId = adminRepo.findUserByAdminId(adminId);
        if (byId == null) {
            return null;
        } else {
            adminDto.setAdminId(byId.getAdminId());
            Admins user = dtoToEntity(adminDto);
            Admins save = adminRepo.save(user);
            return entityToAdminDtoGet(save);
        }
    }

    @Override
    public List<AdminDtoGet> getAllAdmin() {
        List<Admins> all = adminRepo.findAll();
        List<AdminDtoGet> allUsers = new ArrayList<>();
        for (Admins user : all) {
            AdminDtoGet userDto = entityToAdminDtoGet(user);
            allUsers.add(userDto);
        }
        return allUsers;
    }

    @Override
    public AdminDtoGet getAdminById(String id) {
        Admins byId = adminRepo.findUserByAdminId(Long.valueOf(id));
        return entityToAdminDtoGet(byId);
    }

    @Override
    public AdminDto deleteAdmin(Long adminId) {
        Optional<Admins> byId = adminRepo.findById(adminId);
        if (byId.isPresent()){
            adminRepo.deleteById(adminId);
            return entityToDto(byId.get());
        }else {
            return null;
        }
    }


    private Admins dtoToEntity(AdminDto userDto) {
        return modelMapper.map(userDto, Admins.class);
    }

    private AdminDto entityToDto(Admins name) {
        return (name == null) ? null : modelMapper.map(name, AdminDto.class);
    }

    private AdminDtoGet entityToAdminDtoGet(Admins name) {
        return (name == null) ? null : modelMapper.map(name, AdminDtoGet.class);
    }
}
