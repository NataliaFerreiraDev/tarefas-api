package br.com.tarefas_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para transferência de dados do Item.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    /**
     * Descrição do item. Campo obrigatório.
     */
    @NotBlank(message = "A descrição do item é obrigatória.")
    @Schema(description = "Descrição do item.", example = "Enviar relatório mensal")
    private String descricao;

    /**
     * Indica se o item foi concluído.
     */
    @NotNull(message = "O status do item é obrigatório.")
    @Schema(description = "Indica se o item foi concluído.", example = "false")
    private Boolean concluido;

    /**
     * Data limite para conclusão do item.
     */
    @Schema(description = "Data limite para conclusão do item.", example = "2024-12-31T23:59:59")
    private LocalDateTime dataLimite;

    /**
     * ID da categoria associada ao item.
     */
    @NotNull(message = "A categoria do item é obrigatória.")
    @Schema(description = "ID da categoria associada ao item.", example = "1")
    private UUID categoriaId;

}
