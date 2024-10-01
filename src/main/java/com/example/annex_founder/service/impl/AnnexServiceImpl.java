package com.example.annex_founder.service.impl;

import com.example.annex_founder.dto.AnnexDto;
import com.example.annex_founder.dto.get.AnnexDtoGet;
import com.example.annex_founder.dto.HouseRenterDto;
import com.example.annex_founder.model.Annex;
import com.example.annex_founder.model.HouseRenter;
import com.example.annex_founder.repo.AnnexRepo;
import com.example.annex_founder.service.AnnexService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnnexServiceImpl implements AnnexService {

    private final AnnexRepo annexRepo;
    private final ModelMapper modelMapper;

    public AnnexServiceImpl(AnnexRepo annexRepo, ModelMapper modelMapper) {
        this.annexRepo = annexRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public AnnexDto saveAnnex(AnnexDto annexDto) {
        Annex annex = dtoToEntity(annexDto);
        Annex save = annexRepo.save(annex);
        return entityToAnnexDto(save);
    }

    @Override
    public List<AnnexDtoGet> getAllAnnex() {
        List<Annex> all = annexRepo.findAll();
        ArrayList<AnnexDtoGet> allAnnex = new ArrayList<>();
        for (Annex annex : all) {
            AnnexDtoGet annexDto = entityToAnnexDtoGet(annex);
            allAnnex.add(annexDto);
        }
        return allAnnex;
    }

    @Override
    public AnnexDtoGet searchAnnex(Long annexId) {
        Optional<Annex> byId = annexRepo.findById(annexId);
        return byId.map(this::entityToAnnexDtoGet).orElse(null);
    }

    @Override
    public AnnexDto updateAnnex(Long annexId, AnnexDto annexDto) {
        Optional<Annex> byId = annexRepo.findById(annexId);
        if (byId.isPresent()) {
            Annex annex = dtoToEntity(annexDto);
            annex.setAnnexId(byId.get().getAnnexId());
            Annex save = annexRepo.save(annex);
            return entityToAnnexDto(save);
        } else {
            return null;
        }
    }

    @Override
    public AnnexDto deleteAnnex(Long annexId) {
        Optional<Annex> byId = annexRepo.findById(annexId);
        if (byId.isPresent()) {
            annexRepo.deleteById(annexId);
            return entityToAnnexDto(byId.get());
        } else {
            return null;
        }
    }

    private AnnexDto entityToAnnexDto(Annex save) {
        return modelMapper.map(save, AnnexDto.class);
    }

    private AnnexDtoGet entityToAnnexDtoGet(Annex save) {
        AnnexDtoGet map = modelMapper.map(save, AnnexDtoGet.class);
        map.setRenterDto(entityToHouseRentDto(save.getHouseRenter()));
        return map;
    }

    private HouseRenterDto entityToHouseRentDto(HouseRenter houseRenter) {
        return modelMapper.map(houseRenter, HouseRenterDto.class);
    }

    private Annex dtoToEntity(AnnexDto dto) {
        Annex map = modelMapper.map(dto, Annex.class);
        map.setHouseRenter(new HouseRenter(dto.getRenterId()));
        return map;
    }
}
