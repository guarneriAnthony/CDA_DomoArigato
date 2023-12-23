package com.lacorp.backend.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Erreur de statut HTTP")
public class HttpStatusErrorException extends RuntimeException {
    public HttpStatusErrorException(String message) {
        super(message);
    }
}
