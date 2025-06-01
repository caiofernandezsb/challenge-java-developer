package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credit")
@Tag(name = "Credit Controller", description = "Endpoints for credit eligibility evaluation")
public class CreditController {

    @Autowired
    private CreditService creditService;

    @Operation(
            summary = "Check client credit eligibility for vehicle model",
            description = "Checks whether the specified client is eligible for credit based on the selected vehicle model. " +
                    "Eligibility depends on age and income according to business rules."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eligibility result returned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/check")
    public boolean checkCredit(
            @Parameter(description = "Client ID to evaluate", example = "1")
            @RequestParam String clientId,

            @Parameter(description = "Vehicle model (HATCH or SUV)", example = "HATCH")
            @RequestParam VehicleModel vehicleModel
    ) {
        return creditService.checkCredit(clientId, vehicleModel);
    }

}
