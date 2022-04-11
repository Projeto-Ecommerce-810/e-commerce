package br.com.letscode.shop.domain.model.exchange;

import br.com.letscode.shop.domain.model.entity.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CarrinhoRequest {

    private Long user_id;
    private List<Long> produto_id;
}
