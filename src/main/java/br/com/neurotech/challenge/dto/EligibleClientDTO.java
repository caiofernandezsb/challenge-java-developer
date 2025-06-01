package br.com.neurotech.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EligibleClientDTO {

    private String name;
    private double income;

}
