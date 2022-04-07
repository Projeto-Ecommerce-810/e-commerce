package br.com.letscode.shop.produto;

import br.com.letscode.shop.domain.model.entity.FabricanteEntity;
import br.com.letscode.shop.domain.model.entity.ProdutoEntity;
import br.com.letscode.shop.domain.model.exchange.ProdutoRequest;
import br.com.letscode.shop.domain.repository.FabricanteRepository;
import br.com.letscode.shop.domain.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@AllArgsConstructor
@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private FabricanteRepository fabricanteRepository;

    public Page<ProdutoEntity> buscarTodos(Integer offset,
                                           Integer limit,
                                           String nome,
                                           BigDecimal valor
    ) {
        Pageable pageable = new OffsetLimitPageable(offset, limit);

        return produtoRepository.findAll(
                where(nomeContem(nome)).and(valorMenorQue(valor)),
                pageable
        );
    }

    public ProdutoEntity alterarPorId(
            Long id,
            String nome,
            BigDecimal valor,
            String descricao,
            Long fabricante,
            Integer peso
    ){
        ProdutoEntity produto = produtoRepository.findById(id).get();

        alterEntity(produto, nome, valor, descricao, fabricante, peso);
        produtoRepository.save(produto);

        return produto;
    }


    //public ProdutoEntity buscarPorId(Long id){
        //return produtoRepository.findById(id).get();//TODO adicionar tratativa para optional empty
    //}

    public ProdutoEntity buscarPorCodigoBarra(String codigoBarra){
            return produtoRepository.findByCodigoBarra(codigoBarra);
    }

    public ProdutoEntity criar(ProdutoRequest produtoRequest){

        Optional<FabricanteEntity> fabricanteEntity = fabricanteRepository.findById(produtoRequest.getIdFabricante());

        ProdutoEntity produtoEntity = toEntity(produtoRequest, fabricanteEntity.get());

        return produtoRepository.save(produtoEntity);
    }

//    public ProdutoEntity atualizar(Long id, ProdutoRequest produtoRequest){
//
//        Optional<ProdutoEntity> optProdutoEntity = produtoRepository.findById(id);
//        //valido se recuperou ou 404
//        ProdutoEntity produtoEntity = optProdutoEntity.get();
//
//        produtoEntity.setValor(produtoRequest.getValor());
//
//        return produtoRepository.save(toEntity(re));
//    }

//    public ProdutoEntity buscarPorId(Long id){
//        return produtoRepository.findById(id).orElseThrow(() -> );
//    }


    public void deleteById(Long id){
        produtoRepository.deleteById(id);
    }


    private  ProdutoEntity toEntity(ProdutoRequest produtoRequest,
                                    FabricanteEntity fabricante){
        return new ProdutoEntity(
                produtoRequest.getNome(),
                produtoRequest.getDescricao(),
                produtoRequest.getValor(),
                produtoRequest.getCodigoBarra(),
                fabricante,
                produtoRequest.getPeso(),
                produtoRequest.getPesoUnidadeMedida()
        );
    }

    private ProdutoEntity alterEntity(ProdutoEntity entity,
                                            String nome,
                                            BigDecimal valor,
                                            String descricao,
                                            Long fabricante,
                                            Integer peso
    ){
        if (nome != null){
            entity.setNome(nome);
        }
        if (valor != null){
            entity.setValor(valor);
        }
        if (descricao != null){
            entity.setDescricao(descricao);
        }
        if (fabricante != null){
            entity.setFabricante(fabricanteRepository.getById(fabricante));
        }
        if (peso != null){
            entity.setPeso(peso);
        }

        return entity;
    }

    static Specification<ProdutoEntity> valorMenorQue(BigDecimal valor) {
        return valor == null ? null : (produtoEntity, cq, cb) -> cb.lessThanOrEqualTo(produtoEntity.get("valor"),valor);
    }
    static Specification<ProdutoEntity> nomeContem(String nome) {
        return nome == null ? null : (produtoEntity, cq, cb) -> cb.like(produtoEntity.get("nome"), "%" + nome + "%");
    }

}
