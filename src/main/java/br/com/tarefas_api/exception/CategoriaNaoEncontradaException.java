package br.com.tarefas_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * Exceção lançada quando uma categoria não é encontrada.
 */
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoriaNaoEncontradaException extends CategoriaException {

    /**
     * ID da categoria que não foi encontrada.
     */
    private final UUID id;

    /**
     * Construtor da exceção.
     *
     * @param id ID da categoria não encontrada.
     */
    public CategoriaNaoEncontradaException(UUID id) {
        super("Categoria não encontrada com ID: " + id);
        this.id = id;
    }

}
