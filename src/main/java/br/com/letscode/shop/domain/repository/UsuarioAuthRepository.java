package br.com.letscode.shop.domain.repository;

import br.com.letscode.shop.domain.model.entity.UsuarioAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioAuthRepository extends JpaRepository<UsuarioAuthEntity, Long> {
    Optional<UsuarioAuthEntity> findByUsuario(String username);
}
