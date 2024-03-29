package com.example.mintos.backend.services;

import com.example.mintos.backend.entities.Account;
import com.example.mintos.backend.entities.Client;
import com.example.mintos.backend.mappers.ResponseMapper;
import com.example.mintos.backend.models.requests.AccountCreateRequestDto;
import com.example.mintos.backend.models.requests.ClientCreateRequestDto;
import com.example.mintos.backend.models.responses.AccountResponseDto;
import com.example.mintos.backend.models.responses.ClientResponseDto;
import com.example.mintos.backend.repositories.AccountRepository;
import com.example.mintos.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.mintos.backend.utils.StaticHelpers.getPageable;


@Service
public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private static final String CLIENT404 = "Client not found with id ";

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ResponseMapper responseMapper;

    @Autowired
    ClientService(
            ClientRepository clientRepository,
            AccountRepository accountRepository,
            ResponseMapper responseMapper
    ) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.responseMapper = responseMapper;
    }

    public AccountResponseDto createAccount(AccountCreateRequestDto accountCreateRequestDto) {

        Client client = clientRepository
                .findById(accountCreateRequestDto.getId())
                .orElseThrow(() -> new RuntimeException(CLIENT404 + accountCreateRequestDto.getId()));

        Account newAccount = new Account();
        newAccount.setClient(client);
        newAccount.setCurrency(accountCreateRequestDto.getCurrency());
        newAccount.setAmount(0.0);
        return responseMapper.map(accountRepository.saveAndFlush(newAccount));
    }

    public Page<ClientResponseDto> getClients(Integer page, Integer size) {

        Pageable pageable = getPageable(page, size);
        return clientRepository.findAll(pageable).map(responseMapper::map);
    }

    public ClientResponseDto registerClient(ClientCreateRequestDto clientRequest) {
        Client client = new Client();
        client.setName(clientRequest.getName());
        return responseMapper.map(clientRepository.saveAndFlush(client));
    }

    public ClientResponseDto getClient(Long id) {
        return responseMapper
                .map(
                        clientRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException("Client not found"))
                );
    }
}
