package com.datantt.credito.infraestructure.repositories;

import java.util.List;

import org.bson.Document;

import com.datantt.credito.domain.dtos.CreditDTO;
import com.datantt.credito.domain.dtos.CustomerDTO;
import com.datantt.credito.domain.dtos.PaymentDTO;

public interface CreditRepository {

    List<Document> getList(CustomerDTO customerDTO);

    Document getDetail(String id);

    Boolean create(CreditDTO creditDTO);

    List<Document> getListPayment(CreditDTO creditDTO);

    Boolean createPayment(PaymentDTO paymentDTO);

}
