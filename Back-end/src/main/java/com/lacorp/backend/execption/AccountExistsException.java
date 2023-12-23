package com.lacorp.backend.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Account exists exception.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Account already exists")
public class AccountExistsException extends Exception {
}
