package br.com.desafiocasasbahia.domain.vendedor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Filial {

    private String nome;
    private String documento;
    private String cidade;
    private String UF;
    private String tipo;
    private LocalDate dataCadastro;
    private LocalDateTime ultimaAtualizacao;
}
