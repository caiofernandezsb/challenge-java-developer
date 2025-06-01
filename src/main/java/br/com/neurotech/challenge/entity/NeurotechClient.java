package br.com.neurotech.challenge.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Schema(description = "Client entity representing personal and financial information.")
public class NeurotechClient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(hidden = true)
	private Long id;

	@Schema(description = "Full name of the client. Must not be blank.")
	@NotBlank(message = "O nome do cliente deve ser informado")
	private String name;

	@Schema(description = "Age of the client. Must be at least 18.")
	@NotNull(message = "Age is required")
	@Min(value = 18, message = "O cliente deve ter idade igual ou superior à 18 anos")
	private Integer age;

	@Schema(description = "Monthly income of the client. Must be greater than or equal to 0.")
	@NotNull(message = "Income is required")
	@DecimalMin(value = "0.0", message = "A renda deve ser maior ou igual a R$ 0,00")
	private Double income;

}