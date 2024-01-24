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
public class CustomerDTO {

    @JsonProperty("_id")
    public String _id;    

    @JsonProperty("CustomerTypeBusinessCode")
    public String CustomerTypeBusinessCode;

}
