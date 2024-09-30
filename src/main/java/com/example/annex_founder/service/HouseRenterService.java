package com.example.annex_founder.service;

import com.example.annex_founder.dto.HouseRenterDto;

import java.util.List;

public interface HouseRenterService {
    HouseRenterDto saveHouseRenter(HouseRenterDto houseRenterDto);

    boolean isExitHouseRenter(HouseRenterDto houseRenterDto);

    List<HouseRenterDto> getAllHouseRenter();

    HouseRenterDto updateHouseRenter(Long houseRenterId, HouseRenterDto houseRenterDto);

    HouseRenterDto deleteHouseRenter(Long renterId);

    HouseRenterDto searchHouseRenter(Long renterId);
}
