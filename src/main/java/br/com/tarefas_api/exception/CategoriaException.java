package br.com.tarefas_api.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Exceção base para erros relacionados a categorias.
 */
@Getter
@Schema(description = "Exceção base para erros de categorias.")
public class CategoriaException extends RuntimeException {

    /**
     * Construtor da exceção base.
     *
     * @param message Mensagem do erro.
     */
    public CategoriaException(String message) {
        super(message);
    }

}
