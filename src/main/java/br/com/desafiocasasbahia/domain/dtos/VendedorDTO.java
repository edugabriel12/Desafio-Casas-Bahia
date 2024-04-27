package br.com.desafiocasasbahia.domain.dtos;

import br.com.desafiocasasbahia.domain.enums.TipoContratacao;

public record VendedorDTO(String nome, String sobrenome, String dataNascimento,
                          String documento, String email, TipoContratacao tipoContratacao,
                          String documentoFilial) {
}
