package br.com.desafiocasasbahia.responseFeign;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Filial (String nome, String documento, String cidade,
                      String UF, String tipo,
                      LocalDate dataCadastro, LocalDateTime ultimaAtualizacao) {
}
