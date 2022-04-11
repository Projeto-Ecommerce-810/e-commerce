package br.com.letscode.shop.domain.repository;

import br.com.letscode.shop.domain.model.entity.CarrinhoEntity;
import br.com.letscode.shop.domain.model.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<CarrinhoEntity, Long> {

    @Query(value = "SELECT * FROM CARRINHO where id = ?1", nativeQuery = true)
    CarrinhoEntity findByUserId(Long id);
}
