package br.com.letscode.shop.domain.controller;

import br.com.letscode.shop.domain.model.entity.CarrinhoEntity;
import br.com.letscode.shop.domain.model.exchange.CarrinhoRequest;
import br.com.letscode.shop.domain.model.exchange.CarrinhoResponse;
import br.com.letscode.shop.domain.service.CarrinhoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("carrinhos")
@AllArgsConstructor
public class CarrinhoController {

    private CarrinhoService carrinhoService;

    @GetMapping("/{id}")
    public ResponseEntity<CarrinhoResponse> getUserCart(
        @PathVariable(name = "id") Long id
    ){
        CarrinhoResponse response = carrinhoService.getUserCart(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<CarrinhoEntity> createCart(
            @RequestBody CarrinhoRequest request
    ){
        CarrinhoEntity entity = carrinhoService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entity);
    }

    @PostMapping("/adicionar/{id}/{idProduto}")
    public ResponseEntity<CarrinhoEntity> addItem(
            @PathVariable(name = "id") Long id,
            @PathVariable(name = "idProduto") Long idProduto
    ){
        CarrinhoEntity entity = carrinhoService.addItem(id, idProduto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entity);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Long> deleteCart(
            @PathVariable(name = "id") Long id
    ){
        carrinhoService.deleteById(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
