package io.github.psm0015.libraryapi.exceptions;

import lombok.Getter;

public class CampoInvalidoException extends RuntimeException{

    @Getter
    private String campo;

    public CampoInvalidoException(String message, String campo) {
        super(message);
        this.campo = campo;
    }
}
