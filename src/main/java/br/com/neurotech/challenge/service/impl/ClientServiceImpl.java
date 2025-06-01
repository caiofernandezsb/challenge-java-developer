package br.com.neurotech.challenge.service.impl;

import br.com.neurotech.challenge.dto.EligibleClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.repository.ClientRepository;
import br.com.neurotech.challenge.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public String save(NeurotechClient client) {
        NeurotechClient savedClient = clientRepository.save(client);
        return String.valueOf(savedClient.getId());
    }

    @Override
    public NeurotechClient get(String id) {
        return clientRepository.findById(Long.valueOf(id)).orElse(null);
    }

    @Override
    public List<EligibleClientDTO> getEligibleClientsForHatchFixRate() {
        return clientRepository.findAll().stream().filter(
                client -> {
                    int age = client.getAge();
                    double income = client.getIncome();

                    boolean ageBetween23And49 = age >= 23 && age <= 49;
                    boolean isEligibleForFixedRate = age >= 18 && age <= 25;
                    boolean isEligibleForHatch = income >= 5000 && income <= 15000;

                    return ageBetween23And49 && isEligibleForFixedRate && isEligibleForHatch;
                }
        ).map(client -> new EligibleClientDTO(client.getName(), client.getIncome()))
                .toList();
    }

}
