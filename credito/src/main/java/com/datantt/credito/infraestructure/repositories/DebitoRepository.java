package com.datantt.credito.infraestructure.repositories;


import com.datantt.credito.domain.dtos.CustomerDTO;
import com.datantt.credito.domain.dtos.DebitoDto;
import org.bson.Document;

import java.util.List;

public interface DebitoRepository {


    List<Document> getList(CustomerDTO customerDTO);
    Boolean create(DebitoDto debitoDto);
}
