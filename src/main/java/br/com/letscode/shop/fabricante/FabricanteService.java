package br.com.letscode.shop.fabricante;

import br.com.letscode.shop.produto.ProdutoEntity;
import br.com.letscode.shop.produto.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FabricanteService {

    private FabricanteRepository fabricanteRepository;
    private ProdutoRepository produtoRepository;

    public List<FabricanteEntity> getAll(){
        return fabricanteRepository.findAll();
    }

    public FabricanteEntity criar(FabricanteRequest request) {
        FabricanteEntity entity = toEntity(request);

        fabricanteRepository.save(entity);
        return entity;
    }

    private FabricanteEntity toEntity(FabricanteRequest request){
        FabricanteEntity entity = new FabricanteEntity();
        entity.setNome(request.getNome());

        return entity;
    }
}
