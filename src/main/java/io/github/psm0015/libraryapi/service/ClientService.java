package io.github.psm0015.libraryapi.service;

import io.github.psm0015.libraryapi.model.Client;
import io.github.psm0015.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){

        String clientSecretEncoded = encoder.encode(client.getClientSecret());
        client.setClientSecret(clientSecretEncoded);
        return clientRepository.save(client);
    }

    public Client obterPorClientId(String clientdId){
        return clientRepository.findByClientId(clientdId);
    }

}
