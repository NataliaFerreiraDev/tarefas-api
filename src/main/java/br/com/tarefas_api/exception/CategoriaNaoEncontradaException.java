package br.com.tarefas_api.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Exceção lançada quando uma categoria não é encontrada.
 */
@Getter
@Schema(description = "Exceção para indicar que uma categoria não foi encontrada.")
public class CategoriaNaoEncontradaException extends CategoriaException {

    /**
     * ID da categoria que não foi encontrada.
     */
    @Schema(description = "ID da categoria não encontrada.", example = "1")
    private final Long id;

    /**
     * Construtor da exceção.
     *
     * @param id ID da categoria não encontrada.
     */
    public CategoriaNaoEncontradaException(Long id) {
        super("Categoria não encontrada com ID: " + id);
        this.id = id;
    }

}
