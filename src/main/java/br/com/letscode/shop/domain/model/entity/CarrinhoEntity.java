package br.com.letscode.shop.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "CARRINHO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idUsuario;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<ProdutoEntity> produtos;
}
