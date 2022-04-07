package br.com.letscode.shop.fabricante;

import br.com.letscode.shop.domain.model.FabricanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FabricanteRepository extends JpaRepository<FabricanteEntity, Long> {
}
