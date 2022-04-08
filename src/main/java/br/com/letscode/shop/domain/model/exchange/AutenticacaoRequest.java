package br.com.letscode.shop.domain.model.exchange;

import lombok.Data;

@Data
public class AutenticacaoRequest {
    private String login;
    private String senha;
}
