package br.com.tarefas_api.exception;

import lombok.Getter;

/**
 * Exceção base para erros relacionados aos itens.
 */
@Getter
public class ItemException extends RuntimeException{

    /**
     * Construtor da exceção base.
     *
     * @param message Mensagem do erro.
     */
    public ItemException(String message) {
        super(message);
    }

}
