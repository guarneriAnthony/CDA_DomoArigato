package com.lacorp.backend.service;

import com.lacorp.backend.mapper.HueMapper;
import com.lacorp.backend.model.HueAccountInputDTO;
import com.lacorp.backend.model.HueRepositoryModel;
import com.lacorp.backend.repository.HueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HueService {

    private final HueMapper hueMapper = HueMapper.INSTANCE;
    @Autowired
    private HueRepository hueRepository;


    public void saveAccount(HueAccountInputDTO hueAccountInputDTO) {
        HueRepositoryModel hueRepositoryModel = hueMapper.hueAccountInputDtoToHueRepositoryModel(hueAccountInputDTO);
        hueRepository.save(hueRepositoryModel);
    }
}
