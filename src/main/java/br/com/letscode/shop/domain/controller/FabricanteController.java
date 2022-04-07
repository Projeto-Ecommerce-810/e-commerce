package br.com.letscode.shop.fabricante;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/fabricantes")
public class FabricanteController {

    private FabricanteService fabricanteService;

    @GetMapping
    public ResponseEntity<List<FabricanteEntity>> get(

    ){
        List<FabricanteEntity> fabricantes = fabricanteService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fabricantes);
    }

    @PostMapping
    public ResponseEntity<FabricanteEntity> create(@RequestBody FabricanteRequest request){

        FabricanteEntity entity = fabricanteService.criar(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entity);
    }
}
