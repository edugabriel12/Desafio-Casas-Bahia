package br.com.desafiocasasbahia.repositories;

import br.com.desafiocasasbahia.domain.vendedor.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    Vendedor findVendedorByMatricula(String matricula);
}
