package br.com.desafiocasasbahia.domain.vendedor;

import br.com.desafiocasasbahia.domain.enums.TipoContratacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vendedores")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String matricula;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String documento;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoContratacao tipoContratacao;

    @ManyToOne
    @JoinColumn(name = "filial_id", nullable = false)
    private Filial filial;
}
