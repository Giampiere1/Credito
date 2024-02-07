package com.datantt.credito.domain.services.Impl;

import com.datantt.credito.domain.dtos.CreditDTO;
import com.datantt.credito.domain.dtos.CustomerDTO;
import com.datantt.credito.domain.dtos.DebitoDto;
import com.datantt.credito.domain.services.DebitoService;
import com.datantt.credito.domain.util.Constantes;
import com.datantt.credito.domain.util.Util;
import com.datantt.credito.infraestructure.repositories.CreditRepository;
import com.datantt.credito.infraestructure.repositories.DebitoRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;


@Service
public class DebitoServiceImpl implements DebitoService {

    @Autowired
    private DebitoRepository debitoRepository;
    @Override
    public List<DebitoDto> getList(CustomerDTO customerDTO) {
        List<Document> documents = debitoRepository.getList(customerDTO);
        List<DebitoDto> result = Util.mapTo(documents, DebitoDto.class);
        return result;
    }
    @Override
    public Boolean create(DebitoDto debitoDto) {
        Boolean result;
        Boolean validate = validateCredit.test(debitoDto);
        if (!validate) {
            System.out.println("Error en validacion de credito.");
            return false;
        }
        if (debitoDto.getTypeCode()
                .equalsIgnoreCase(Constantes.TIPOCLIENTE_PERSONAL)) {
            System.out.println("TIPOCLIENTE_PERSONAL");
            CustomerDTO customer = new CustomerDTO();
            customer.set_id(debitoDto.getCustomer_Id());
            List<Document> documents = debitoRepository.getList(customer);
            List<CreditDTO> creditos = Util.mapTo(documents, CreditDTO.class);
            System.out.println("NUMERO DE CREDITOS");
            System.out.println(creditos.size());
            if (creditos.size() >= 1) {
                System.out.println("Error en validacion de creditos.");
                return false;
            }
        }
        if (debitoDto.getTypeCode()
                .equalsIgnoreCase(Constantes.TIPOCLIENTE_EMPRESARIAL)) {
            System.out.println("TIPOCLIENTE_PERSONAL");
            CustomerDTO customer = new CustomerDTO();
            customer.set_id(debitoDto.getCustomer_Id());
            List<Document> documents = debitoRepository.getList(customer);
            List<CreditDTO> creditos = Util.mapTo(documents, CreditDTO.class);
            boolean debe=creditos.stream().anyMatch(x->x.Estado.equals("NP"));
            if (debe) {
                System.out.println("Debe un credito");
                return false;
            }

        }
        result = debitoRepository.create(debitoDto);
        return result;
    }
    Predicate<DebitoDto> validateCredit = debitoDto -> (debitoDto.getTypeCode()
            .equalsIgnoreCase(Constantes.TIPOCLIENTE_PERSONAL)
            || debitoDto.getTypeCode().equalsIgnoreCase(Constantes.TIPOCLIENTE_EMPRESARIAL)
            && Double.parseDouble(debitoDto.getAmount()) > 0);
}
