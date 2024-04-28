package br.com.desafiocasasbahia.exceptions.restExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestExceptionHandlerResponse {

    private String httpStatusMessage;
    private int HttpStatusCode;
    private String message;
}
