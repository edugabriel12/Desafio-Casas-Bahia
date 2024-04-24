package br.com.desafiocasasbahia.domain.vendedor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "filiais")
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nome;
    private String documento;
    private String cidade;
    private String UF;
    private String tipo;
    private LocalDate dataCadastro;
    private LocalDateTime ultimaAtualizacao;
}
