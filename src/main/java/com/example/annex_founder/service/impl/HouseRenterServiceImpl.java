package com.example.annex_founder.service.impl;

import com.example.annex_founder.dto.HouseRenterDto;
import com.example.annex_founder.model.HouseRenter;
import com.example.annex_founder.repo.HouseRenterRepo;
import com.example.annex_founder.service.HouseRenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HouseRenterServiceImpl implements HouseRenterService {

    private final HouseRenterRepo houseRenterRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public HouseRenterServiceImpl(HouseRenterRepo houseRenterRepo, ModelMapper modelMapper) {
        this.houseRenterRepo = houseRenterRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public HouseRenterDto saveHouseRenter(HouseRenterDto houseRenterDto) {
        HouseRenter houseRenter = dtoToEntity(houseRenterDto);
        HouseRenter save = houseRenterRepo.save(houseRenter);
        return entityToHouseRenterDto(save);
    }

    @Override
    public boolean isExitHouseRenter(HouseRenterDto houseRenterDto) {
        HouseRenter houseRenter = houseRenterRepo.findByEmail(houseRenterDto.getEmail());
        return houseRenter != null;
    }

    @Override
    public List<HouseRenterDto> getAllHouseRenter() {
        List<HouseRenter> all = houseRenterRepo.findAll();
        ArrayList<HouseRenterDto> objects = new ArrayList<>();
        for (HouseRenter houseRenter : all) {
            objects.add(entityToHouseRenterDto(houseRenter));
        }
        return objects;
    }

    @Override
    public HouseRenterDto updateHouseRenter(Long houseRenterId, HouseRenterDto houseRenterDto) {
        Optional<HouseRenter> byId = houseRenterRepo.findById(houseRenterId);
        if (byId.isPresent()) {
            houseRenterDto.setRenterId(houseRenterId);
            HouseRenter houseRenter = dtoToEntity(houseRenterDto);
            HouseRenter save = houseRenterRepo.save(houseRenter);
            return entityToHouseRenterDto(save);
        } else {
            return null;
        }

    }

    @Override
    public HouseRenterDto deleteHouseRenter(Long renterId) {
        Optional<HouseRenter> byId = houseRenterRepo.findById(renterId);
        if (byId.isPresent()) {
            houseRenterRepo.deleteById(renterId);
            return entityToHouseRenterDto(byId.get());
        } else {
            return null;
        }
    }

    @Override
    public HouseRenterDto searchHouseRenter(Long renterId) {
        Optional<HouseRenter> byId = houseRenterRepo.findById(renterId);
        return byId.map(this::entityToHouseRenterDto).orElse(null);
    }

    private HouseRenterDto entityToHouseRenterDto(HouseRenter save) {
        return modelMapper.map(save, HouseRenterDto.class);
    }

    private HouseRenter dtoToEntity(HouseRenterDto houseRenterDto) {
        return modelMapper.map(houseRenterDto, HouseRenter.class);
    }
}
