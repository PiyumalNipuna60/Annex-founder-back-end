package com.example.annex_founder.service;

import com.example.annex_founder.dto.AnnexDto;
import com.example.annex_founder.dto.get.AnnexDtoGet;

import java.util.List;

public interface AnnexService {

    AnnexDto saveAnnex(AnnexDto annexDto);

    List<AnnexDtoGet> getAllAnnex();

    AnnexDtoGet searchAnnex(Long annexId);

    AnnexDto updateAnnex(Long annexId, AnnexDto annexDto);

    AnnexDto deleteAnnex(Long annexId);
}
