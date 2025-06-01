package br.com.neurotech.challenge.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(String id) {
        super("Cliente de id " + id + " não encontrado");
    }
}
