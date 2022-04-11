package br.com.letscode.shop.domain.service;

import br.com.letscode.shop.domain.model.entity.CarrinhoEntity;
import br.com.letscode.shop.domain.model.entity.ProdutoEntity;
import br.com.letscode.shop.domain.model.exchange.CarrinhoRequest;
import br.com.letscode.shop.domain.model.exchange.CarrinhoResponse;
import br.com.letscode.shop.domain.repository.CarrinhoRepository;
import br.com.letscode.shop.domain.repository.ProdutoRepository;
import br.com.letscode.shop.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CarrinhoService {

    private CarrinhoRepository carrinhoRepository;
    private UsuarioRepository usuarioRepository;
    private ProdutoRepository produtoRepository;

    public CarrinhoResponse getUserCart(Long id){

        CarrinhoEntity entity = carrinhoRepository.findByUserId(id);
        CarrinhoResponse response = new CarrinhoResponse();
        response.setNome(
                usuarioRepository
                        .findById(entity
                        .getIdUsuario())
                        .get()
                        .getNome()
        );
        response.setProdutos(entity.getProdutos());

        return response;
    }

    public CarrinhoEntity create(CarrinhoRequest request) {

        CarrinhoEntity entity = toEntity(request);

        carrinhoRepository.save(entity);
        return entity;
    }

    public CarrinhoEntity addItem(Long id, Long idProduto) {
        CarrinhoEntity entity = carrinhoRepository.findById(id).get();

        List<ProdutoEntity> produtos = entity.getProdutos();
        if (produtoRepository.findId(idProduto) == null){
            System.out.println("produto de id "+ idProduto +" não encontrado.");
        }else{
            produtos.add(produtoRepository.findId(idProduto));
            entity.setProdutos(produtos);
            carrinhoRepository.save(entity);
        }

        return entity;
    }


    private CarrinhoEntity toEntity(CarrinhoRequest request) {
        CarrinhoEntity entity = new CarrinhoEntity();

        entity.setIdUsuario(request.getUser_id());

        List<ProdutoEntity> produtos = new ArrayList<>();
        for (Long id : request.getProduto_id()){
            if (produtoRepository.findId(id) == null){
                System.out.println("produto de id "+ id +" não encontrado.");
            }else{
                produtos.add(produtoRepository.findId(id));
            }

        }

        entity.setProdutos(produtos);

        return entity;
    }


    public void deleteById(Long id) {
        carrinhoRepository.deleteById(id);
    }
}
