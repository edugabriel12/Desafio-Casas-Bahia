package br.com.desafiocasasbahia.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class VendedorAtualizadoResponse {

    private String nome;
    private String sobrenome;
    private LocalDate dataNascimeto;
}
