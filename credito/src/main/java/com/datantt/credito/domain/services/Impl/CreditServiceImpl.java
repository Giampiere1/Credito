package com.datantt.credito.domain.services.Impl;

import java.util.List;
import java.util.function.Predicate;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datantt.credito.domain.dtos.CreditDTO;
import com.datantt.credito.domain.dtos.CustomerDTO;
import com.datantt.credito.domain.dtos.PaymentDTO;
import com.datantt.credito.domain.services.CreditService;
import com.datantt.credito.domain.util.Constantes;
import com.datantt.credito.domain.util.Util;
import com.datantt.credito.infraestructure.repositories.CreditRepository;

@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditRepository creditRepository;

    @Override
    public List<CreditDTO> getList(CustomerDTO customerDTO) {
        List<Document> documents = creditRepository.getList(customerDTO);
        List<CreditDTO> result = Util.mapTo(documents, CreditDTO.class);
        return result;
    }

    @Override
    public Boolean create(CreditDTO creditDTO) {
        Boolean result;
        Boolean validate = validateCredit.test(creditDTO);
        if (!validate) {
            System.out.println("Error en validacion de credito.");
            return false;
        }
        if (creditDTO.getTypeCode()
                .equalsIgnoreCase(Constantes.TIPOCLIENTE_PERSONAL)) {
            System.out.println("TIPOCLIENTE_PERSONAL");
            CustomerDTO customer = new CustomerDTO();
            customer.set_id(creditDTO.getCustomer_Id());
            List<Document> documents = creditRepository.getList(customer);
            List<CreditDTO> creditos = Util.mapTo(documents, CreditDTO.class);
            System.out.println("NUMERO DE CREDITOS");
            System.out.println(creditos.size());
            if (creditos.size() >= 1) {
                System.out.println("Error en validacion de creditos.");
                return false;
            }
        }
        if (creditDTO.getTypeCode()
                .equalsIgnoreCase(Constantes.TIPOCLIENTE_EMPRESARIAL)) {
            System.out.println("TIPOCLIENTE_PERSONAL");
            CustomerDTO customer = new CustomerDTO();
            customer.set_id(creditDTO.getCustomer_Id());
            List<Document> documents = creditRepository.getList(customer);
            List<CreditDTO> creditos = Util.mapTo(documents, CreditDTO.class);
            boolean debe=creditos.stream().anyMatch(x->x.Estado.equals("NP"));
            if (debe) {
                System.out.println("Debe un credito");
                return false;
            }

        }
        result = creditRepository.create(creditDTO);
        return result;
    }

    @Override
    public List<PaymentDTO> getListPayment(CreditDTO creditDTO) {
        List<Document> documents = creditRepository.getListPayment(creditDTO);
        List<PaymentDTO> result = Util.mapTo(documents, PaymentDTO.class);
        return result;
    }

    @Override
    public Boolean createPayment(PaymentDTO paymentDTO) {
        Boolean result;
        Document document = creditRepository.getDetail(paymentDTO.getCredit_Id());
        CreditDTO creditDTO = Util.mapTo(document, CreditDTO.class);
        System.out.println("CREDITO linea");
        System.out.println(creditDTO.getAmount());
        System.out.println("CREDITO consumo");
        System.out.println(creditDTO.getConsumed());
        if (!(paymentDTO.getTypeCode().equalsIgnoreCase(Constantes.TIPOPAGO_PAGO)
                && Double.parseDouble(creditDTO.getConsumed()) - Double.parseDouble(paymentDTO.getAmount()) >= 0)
                && !(paymentDTO.getTypeCode().equalsIgnoreCase(Constantes.TIPOPAGO_CONSUMO)
                        && Double.parseDouble(creditDTO.getAmount()) >= Double.parseDouble(creditDTO.getConsumed())
                                + Double.parseDouble(paymentDTO.getAmount()))) {
            System.out.println("Error en validacion de PAGO.");
            return false;
        }
        result = creditRepository.createPayment(paymentDTO);
        return result;
    }

    Predicate<CreditDTO> validateCredit = credit -> (credit.getTypeCode()
            .equalsIgnoreCase(Constantes.TIPOCLIENTE_PERSONAL)
            || credit.getTypeCode().equalsIgnoreCase(Constantes.TIPOCLIENTE_EMPRESARIAL)
                    && Double.parseDouble(credit.getAmount()) > 0);

}
