package br.com.letscode.shop.domain.controller;

import br.com.letscode.shop.domain.model.exchange.AutenticacaoRequest;
import br.com.letscode.shop.domain.model.exchange.AutenticacaoResponse;
import br.com.letscode.shop.util.TokenUtil;
import br.com.letscode.shop.domain.service.UsuarioDetalheService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("autenticacao")
@RestController
@AllArgsConstructor
public class AutenticacaoController {

    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDetalheService usuarioDetalheService;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping
    ResponseEntity<AutenticacaoResponse> login(
        @RequestBody AutenticacaoRequest request
    ) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getSenha()));
        }catch (DisabledException e){
            throw new Exception("Usuario desabilitado", e);
        }catch  (BadCredentialsException e){
            throw new Exception("Credenciais inválidas", e);
        }

        UserDetails userDetails = usuarioDetalheService.loadUserByUsername(request.getLogin());

        final String token = tokenUtil.gerarToken(userDetails);
        return ResponseEntity.ok(AutenticacaoResponse
                .builder()
                .token(token)
                .build());
    }
}
