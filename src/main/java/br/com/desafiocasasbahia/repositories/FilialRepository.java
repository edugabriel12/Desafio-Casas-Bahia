package br.com.desafiocasasbahia.repositories;

import br.com.desafiocasasbahia.domain.vendedor.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Long> {
}
