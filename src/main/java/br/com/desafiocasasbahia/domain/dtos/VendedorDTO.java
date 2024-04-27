package br.com.desafiocasasbahia.domain.dtos;

import br.com.desafiocasasbahia.domain.enums.TipoContratacao;
import jakarta.validation.constraints.Email;

public record VendedorDTO(String nome, String sobrenome, String dataNascimento,
                          String documento, @Email String email, TipoContratacao tipoContratacao,
                          String documentoFilial) {
}
