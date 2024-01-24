package com.datantt.credito.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    @JsonProperty("_id")
    public String _id;

    @JsonProperty("TypeCode")
    public String TypeCode;

    @JsonProperty("Credit_Id")
    public String Credit_Id;

    @JsonProperty("Amount")
    public String Amount;

}