package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.dto.EligibleClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/client")
@Tag(name = "Client Controller", description = "Operations related to client data and credit eligibility")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    @Operation(
            summary = "Create a new client",
            description = "Registers a new client in the system. A `Location` header is returned with the URI of the newly created client."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    public ResponseEntity<Void> createClient(
            @RequestBody @Valid NeurotechClient client
    ) {
        String clientId = clientService.save(client);
        return ResponseEntity
                .created(URI.create("/api/client/" + clientId))
                .build();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a client by ID",
            description = "Fetches a client's details based on the provided unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found",
                    content = @Content(schema = @Schema(implementation = NeurotechClient.class))),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content)
    })
    public ResponseEntity<NeurotechClient> getClient(
            @PathVariable
            @Parameter(description = "Client ID to be fetched", example = "1")
            String id
    ) {
        NeurotechClient client = clientService.get(id);
        return client != null
                ? ResponseEntity.ok(client)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/hatch-fix-rate")
    @Operation(
            summary = "List clients eligible for Hatch Fix Rate",
            description = "Returns all clients who meet the eligibility criteria for the Hatch Fix Rate vehicle credit."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eligible clients listed successfully"),
            @ApiResponse(responseCode = "204", description = "No eligible clients found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred")
    })
    public ResponseEntity<List<EligibleClientDTO>> getEligibleClientsForHatchFixRate() {
        List<EligibleClientDTO> eligibleClients = clientService.getEligibleClientsForHatchFixRate();

        if (eligibleClients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(eligibleClients);
    }

}
