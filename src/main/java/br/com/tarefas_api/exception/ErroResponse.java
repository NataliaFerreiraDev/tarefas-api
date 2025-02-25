package br.com.tarefas_api.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Classe que representa uma resposta de erro padronizada.
 */
@Getter
@AllArgsConstructor
@Schema(description = "Detalhes do erro retornado pela API.")
public class ErroResponse {

    @Schema(description = "Timestamp do erro.", example = "2024-02-24T10:15:30")
    private final LocalDateTime timestamp;

    @Schema(description = "Código HTTP do erro.", example = "404")
    private final int status;

    @Schema(description = "Título do erro.", example = "Erro de Categoria")
    private final String error;

    @Schema(description = "Mensagem detalhada do erro.", example = "Categoria não encontrada com ID: 1")
    private final String message;

}
