package me.damian.ciepiela.jwtdemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Document already exists!")
public class DocumentAlreadyExistsException extends Exception {
}
