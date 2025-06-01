package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.dto.EligibleClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;

import java.util.List;


public interface ClientService {
	
	/**
	 * Salva um novo cliente
	 * @return ID do cliente recém-salvo
	 */
	String save(NeurotechClient client);
	
	/**
	 * Recupera um cliente baseado no seu ID
	 * @param id Identificador do cliente
	 * @return Cliente correspondente, ou null se não encontrado
	 */
	NeurotechClient get(String id);

	/**
	 * Retorna uma lista de clientes que atendem aos seguintes critérios:
	 * - Idade entre 23 e 49 anos
	 * - Elegíveis para crédito com juros fixos (idade entre 18 e 25 anos)
	 * - Com renda entre R$5.000,00 e R$15.000,00 (aptos a crédito automotivo para veículos do tipo Hatch)
	 *
	 * @return Lista de clientes elegíveis, contendo nome e renda
	 */
	List<EligibleClientDTO> getEligibleClientsForHatchFixRate();

}
