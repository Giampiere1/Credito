package com.datantt.credito.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @JsonFormat(pattern="MM/dd/yyyy")
    @JsonProperty("StartDate")
    public Date StartDate;

    @JsonFormat(pattern="MM/dd/yyyy")
    @JsonProperty("EndDate")
    public Date EndDate;

    @JsonProperty("Estado")
    public String Estado;


}
