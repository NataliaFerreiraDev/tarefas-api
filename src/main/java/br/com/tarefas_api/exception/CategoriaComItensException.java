package br.com.tarefas_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando uma categoria não pode ser excluída ou alterada porque possui itens associados.
 */
@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoriaComItensException extends CategoriaException {

    /**
     * ID da categoria que possui itens associados.
     */
    private final Long id;

    /**
     * Construtor da exceção.
     *
     * @param id ID da categoria que não pode ser excluída/alterada.
     */
    public CategoriaComItensException(Long id) {
        super("Não é possível excluir ou alterar a categoria com ID: " + id + " pois possui itens associados.");
        this.id = id;
    }

}
