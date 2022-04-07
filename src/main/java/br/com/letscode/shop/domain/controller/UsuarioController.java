package br.com.letscode.shop.usuario;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@RequestMapping("usuarios")
@RestController
public class UsuarioController {

    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioEntity>> get(

    ){
        List<UsuarioEntity> usuarios = usuarioService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioEntity> create(
            @RequestBody UsuarioRequest request
    ){
        UsuarioEntity entity = usuarioService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entity);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioEntity> getById(
        @PathVariable(name = "id") Long id
    ){
        UsuarioEntity entity = usuarioService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entity);
    }

    @PatchMapping("/alterar/{id}")
    public ResponseEntity<UsuarioEntity> alterById(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "dataNascimento", required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dataNascimento
    ){
        UsuarioEntity entity = usuarioService.alterById(id, nome, dataNascimento);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entity);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Long> deleteById(
            @PathVariable(name = "id") Long id
    ){
        usuarioService.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
