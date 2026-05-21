package br.com.pontu.api.exceptions;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String msg) {
        super(msg);
    }
}
