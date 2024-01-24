package com.datantt.credito.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.datantt.credito.domain.dtos.CreditDTO;
import com.datantt.credito.domain.dtos.CustomerDTO;
import com.datantt.credito.domain.dtos.PaymentDTO;
import com.datantt.credito.domain.services.CreditService;

import static java.util.Objects.isNull;

@RequestMapping("/credit")
@RestController
public class CreditController {

    @Autowired
    private CreditService creditService;

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<?> getList(@RequestParam Map<String, String> params) {
        CustomerDTO request = new CustomerDTO();
        try {
            System.out.println("INICIO LISTADO");
            request.set_id(params.get("CustomerId"));
            List<CreditDTO> response = creditService.getList(request);
            if (isNull(response)) {
                return ResponseEntity.noContent().build();
            }
            System.out.println("FIN LISTADO");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody CreditDTO creditDTO) {
        try {
            System.out.println("INICIO CREAR");
            Boolean response = creditService.create(creditDTO);
            System.out.println("FIN CREAR");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/payment")
    @ResponseBody
    public ResponseEntity<?> getListPayment(@RequestParam Map<String, String> params) {
        CreditDTO request = new CreditDTO();
        try {
            System.out.println("INICIO LISTADO");
            request.set_id(params.get("CreditId"));
            List<PaymentDTO> response = creditService.getListPayment(request);
            if (isNull(response)) {
                return ResponseEntity.noContent().build();
            }
            System.out.println("FIN LISTADO");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/payment")
    @ResponseBody
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            System.out.println("INICIO CREAR");
            Boolean response = creditService.createPayment(paymentDTO);
            System.out.println("FIN CREAR");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
