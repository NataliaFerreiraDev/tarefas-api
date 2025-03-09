package br.com.tarefas_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO para transferência de dados da Categoria.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaDTO {

    /**
     * Id da categoria. Campo opcional.
     */
    @Schema(description = "Id da categoria.", example = "747ab89b-0e9e-4c21-aeda-36daec191de7")
    private UUID id;

    /**
     * Nome da categoria. Campo obrigatório.
     */
    @NotBlank(message = "O nome da categoria é obrigatório.")
    @Schema(description = "Nome da categoria.", example = "Trabalho")
    private String nome;

}
