package br.com.letscode.shop.domain.service;

import br.com.letscode.shop.domain.model.entity.UsuarioAuthEntity;
import br.com.letscode.shop.domain.repository.UsuarioAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsuarioDetalheService implements UserDetailsService {

    @Autowired
    private UsuarioAuthRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioAuthEntity usuario = repository.findByUsuario(username)
                .orElseThrow(() ->  new UsernameNotFoundException(""+ username));

        return new User(username, usuario.getPassword(),
                new ArrayList<>()
                );
    }
}
