package br.com.letscode.shop.produto;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@RequestMapping("produtos")
@RestController
public class ProdutoController {

    private ProdutoService produtoService;

    @GetMapping()
    public ResponseEntity<Page<ProdutoEntity>> get(
            @RequestParam(name = "offset") Integer offset,
            @RequestParam(name = "limit") Integer limit,
            @RequestParam(name = "nome", required = false) String nome,
//            @RequestParam(name = "dataCriacao") String dataCriacao,
//            @RequestParam(name = "fabricante") String fabricante,
            @RequestParam(name = "valor_maximo", required = false) BigDecimal valorMaximo
    ){
        Page<ProdutoEntity> produtos = produtoService.buscarTodos(offset, limit, nome, valorMaximo);
        return ResponseEntity.ok(produtos);
    }

    @PatchMapping("/alterar/{id}")
    public ResponseEntity<ProdutoEntity> alterar(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "valor", required = false) BigDecimal valor,
            @RequestParam(name = "descricao", required = false) String descricao,
            @RequestParam(name = "fabricante", required = false) Long fabricante,
            @RequestParam(name = "peso", required = false) Integer peso
    ){
        ProdutoEntity entity = produtoService.alterarPorId(id, nome, valor, descricao, fabricante, peso);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entity);
    }

    @PostMapping
    public ResponseEntity<ProdutoEntity> create(
            @RequestBody ProdutoRequest request
    ){
        ProdutoEntity produto = produtoService.criar(request);
        return ResponseEntity.created(null).body(produto);
    }

    @GetMapping("/codigo/{codigoBarra}")
    public ResponseEntity<ProdutoEntity> getByCodigoBarra(
            @PathVariable(name = "codigoBarra") String codigoBarra){

        ProdutoEntity produto = produtoService.buscarPorCodigoBarra(codigoBarra);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Long> deleteById(
        @PathVariable(name = "id") Long id
    ){
        produtoService.deleteById(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
