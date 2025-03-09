package br.com.tarefas_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO para transferência de dados do Item.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDTO {

    /**
     * Id do item. Campo opcional.
     */
    @Size(min = 5, message = "A descrição deve ter no mínimo 5 caracteres.")
    @Schema(description = "Id do item.", example = "747ab89b-0e9e-4c21-aeda-36daec191de7")
    private UUID id;

    /**
     * Descrição do item. Campo obrigatório.
     */
    @NotBlank(message = "A descrição do item é obrigatória.")
    @Schema(description = "Descrição do item.", example = "Enviar relatório mensal")
    private String descricao;

    /**
     * Indica se o item foi concluído.
     */
    @Schema(description = "Indica se o item foi concluído.", example = "false")
    private boolean concluido;

    /**
     * Data de criação do item.
     */
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Schema(description = "Data de criação do item.", example = "09/03/2025 15:00")
    private String dataCriacao;

    /**
     * Data de atualização do item. Campo opcional.
     */
    @Schema(description = "Data da atualização do item.", example = "09/03/2025 15:00")
    private String dataAtualizacao;

    /**
     * Data limite para conclusão do item.
     */
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Schema(description = "Data limite para conclusão do item.", example = "09/03/2025 15:00")
    private String dataLimite;

    /**
     * ID da categoria associada ao item.
     */
    @NotNull(message = "A categoria do item é obrigatória.")
    @Schema(description = "ID da categoria associada ao item.", example = "1")
    private UUID categoriaId;

}
