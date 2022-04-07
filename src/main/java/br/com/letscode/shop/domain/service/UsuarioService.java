package br.com.letscode.shop.usuario;

import br.com.letscode.shop.domain.model.entity.UsuarioEntity;
import br.com.letscode.shop.domain.model.exchange.UsuarioRequest;
import br.com.letscode.shop.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public List<UsuarioEntity> getAll(){
        return usuarioRepository.findAll();
    }

    public UsuarioEntity create(UsuarioRequest request){
        UsuarioEntity entity = toEntity(request);

        usuarioRepository.save(entity);

        return entity;
    }

    public UsuarioEntity findById(Long id) {
        return usuarioRepository.findById(id).get();
    }

    public void deleteById(Long id){
        usuarioRepository.deleteById(id);
    }

    public UsuarioEntity alterById(Long id,
                                   String nome,
                                   ZonedDateTime dataNascimento
    ){
        UsuarioEntity entity = usuarioRepository.findById(id).get();

        alterEntity(entity, nome, dataNascimento);
        usuarioRepository.save(entity);

        return entity;
    }

    private UsuarioEntity alterEntity(UsuarioEntity entity,
                                      String nome,
                                      ZonedDateTime dataNascimento
    ){
        if (nome != null){
            entity.setNome(nome);
        }
        if (dataNascimento != null){
            entity.setDataNascimento(dataNascimento);
        }

        return entity;
    }


    private UsuarioEntity toEntity(UsuarioRequest request){
        UsuarioEntity entity = new UsuarioEntity();

        entity.setNome(request.getNome());
        entity.setDataNascimento(request.getDataNascimento());

        return entity;
    }


}
