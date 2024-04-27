package br.com.desafiocasasbahia.response;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class VendedorAtualizadoResponse {

    private String nome;
    private String sobrenome;
    private LocalDate dataNascimeto;
}
