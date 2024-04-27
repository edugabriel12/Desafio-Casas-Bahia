package br.com.desafiocasasbahia.repositories;

import br.com.desafiocasasbahia.domain.vendedor.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    Vendedor findVendedorByMatricula(String matricula);

    Optional<Vendedor> findByEmail(String email);

    Optional<Vendedor> findByDocumento(String documento);
}
