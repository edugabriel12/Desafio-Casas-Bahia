package br.com.desafiocasasbahia.exceptions;

public class InvalidDocumentException extends RuntimeException {

    public InvalidDocumentException() {
        super();
    }

    public InvalidDocumentException(String message) {
        super(message);
    }

}
