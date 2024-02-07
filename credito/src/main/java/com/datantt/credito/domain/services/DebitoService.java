package com.datantt.credito.domain.services;

import com.datantt.credito.domain.dtos.CreditDTO;
import com.datantt.credito.domain.dtos.CustomerDTO;
import com.datantt.credito.domain.dtos.DebitoDto;

import java.util.List;

public interface DebitoService {

    List<DebitoDto> getList(CustomerDTO customerDTO);
    Boolean create(DebitoDto debitoDto);
}
