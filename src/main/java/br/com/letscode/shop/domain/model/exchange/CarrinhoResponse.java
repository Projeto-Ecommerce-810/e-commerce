package br.com.letscode.shop.domain.model.exchange;

import br.com.letscode.shop.domain.model.entity.ProdutoEntity;
import lombok.Data;

import java.util.List;

@Data
public class CarrinhoResponse {

    private Long user_id;
    private String nome;
    private List<ProdutoEntity> produtos;
}
