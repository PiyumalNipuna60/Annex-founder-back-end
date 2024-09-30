package com.example.annex_founder.service;

import com.example.annex_founder.dto.AdminDto;
import com.example.annex_founder.dto.AdminDtoGet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    AdminDto adminLogin(AdminDto dto);

    AdminDtoGet findAdminByName(String email, String userName);

    AdminDtoGet registerAdmin(AdminDto userDto);

    AdminDtoGet updateAdmin(AdminDto adminDto, Long adminId);

    List<AdminDtoGet> getAllAdmin();

    AdminDtoGet getAdminById(String id);

    AdminDto deleteAdmin(Long adminId);
}
