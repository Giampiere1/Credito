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
public class CreditDTO {

    @JsonProperty("_id")
    public String _id;

    @JsonProperty("Customer_Id")
    public String Customer_Id;

    @JsonProperty("TypeCode")
    public String TypeCode;

    @JsonProperty("Amount")
    public String Amount;

    @JsonProperty("Consumed")
    public String Consumed;

    @JsonProperty("HasCard")
    public String HasCard;

    @JsonProperty("CardNumber")
    public String CardNumber;

}
