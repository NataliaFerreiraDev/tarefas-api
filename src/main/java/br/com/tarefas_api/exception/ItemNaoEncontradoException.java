package br.com.tarefas_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um item não é encontrado.
 */
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNaoEncontradoException extends ItemException    {

    /**
     * ID do item que não foi encontrado.
     */
    private final Long id;

    /**
     * Construtor da exceção.
     *
     * @param id ID do item não encontrado.
     */
    public ItemNaoEncontradoException(Long id) {
        super("Item não encontrado com ID: " + id);
        this.id = id;
    }

}
