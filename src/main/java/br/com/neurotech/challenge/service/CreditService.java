package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.VehicleModel;

public interface CreditService {

	/**
	 * Verifica se o cliente está apto a receber crédito para o modelo de veículo especificado.
	 *
	 * @param clientId ID do cliente a ser verificado
	 * @param model Modelo do veículo (ex: HATCH ou SUV)
	 * @return true se o cliente for elegível para o modelo informado, false caso contrário
	 */
	boolean checkCredit(String clientId, VehicleModel model);
	
}
