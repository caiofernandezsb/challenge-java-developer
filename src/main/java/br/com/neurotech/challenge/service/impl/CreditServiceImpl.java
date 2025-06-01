package br.com.neurotech.challenge.service.impl;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.repository.ClientRepository;
import br.com.neurotech.challenge.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public boolean checkCredit(String clientId, VehicleModel model) {
        NeurotechClient client = clientRepository.findById(Long.valueOf(clientId))
                .orElseThrow(() -> new ClientNotFoundException(clientId));

        double income = client.getIncome();
        int age = client.getAge();

        if (model == VehicleModel.HATCH){
            return income >= 5000 && income <= 15000;
        } else if (model == VehicleModel.SUV){
            return income > 8000 && age > 20;
        }

        return false;

    }
}
