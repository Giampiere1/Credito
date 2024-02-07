package com.datantt.credito.controllers;

import com.datantt.credito.domain.dtos.CreditDTO;
import com.datantt.credito.domain.dtos.DebitoDto;
import com.datantt.credito.domain.services.CreditService;
import com.datantt.credito.domain.services.DebitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/debid")
@RestController
public class DebitoController {

    @Autowired
    private DebitoService debitoService;
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody DebitoDto debitoDto) {
        try {
            System.out.println("INICIO CREAR");
            Boolean response = debitoService.create(debitoDto);
            System.out.println("FIN CREAR");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
