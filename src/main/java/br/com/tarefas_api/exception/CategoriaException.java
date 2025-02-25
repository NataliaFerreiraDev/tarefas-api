package br.com.tarefas_api.exception;

import lombok.Getter;

/**
 * Exceção base para erros relacionados a categorias.
 */
@Getter
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
