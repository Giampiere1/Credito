package com.datantt.credito.domain.services;

import java.util.List;

import com.datantt.credito.domain.dtos.CreditDTO;
import com.datantt.credito.domain.dtos.CustomerDTO;
import com.datantt.credito.domain.dtos.PaymentDTO;


public interface CreditService {

    List<CreditDTO> getList(CustomerDTO customerDTO);

    Boolean create(CreditDTO bankAccount);

    List<PaymentDTO> getListPayment(CreditDTO bankAccount);

    Boolean createPayment(PaymentDTO transactionDTO);

}
