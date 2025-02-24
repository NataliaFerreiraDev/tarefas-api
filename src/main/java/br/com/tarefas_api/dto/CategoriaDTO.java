package br.com.tarefas_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para transferência de dados da Categoria.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    /**
     * Nome da categoria. Campo obrigatório.
     */
    @NotBlank(message = "O nome da categoria é obrigatório.")
    @Schema(description = "Nome da categoria.", example = "Trabalho")
    private String nome;

}
