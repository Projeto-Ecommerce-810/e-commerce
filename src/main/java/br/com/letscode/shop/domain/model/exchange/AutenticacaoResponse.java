package br.com.letscode.shop.domain.model.exchange;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AutenticacaoResponse {
    private String token;
}
