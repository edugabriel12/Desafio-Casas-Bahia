package br.com.desafiocasasbahia.domain;

import br.com.desafiocasasbahia.domain.enums.TipoContratacao;
import br.com.desafiocasasbahia.responseFeign.Filial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class Vendedor {
    
    private String matricula;
    
    private String nome;
    
    private String sobrenome;
    
    private LocalDate dataNascimento;
    
    private String documento;
    
    private String email;
    
    private TipoContratacao tipoContratacao;
    
    private Filial filial;
}
