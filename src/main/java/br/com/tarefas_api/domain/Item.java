package br.com.tarefas_api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa um item dentro de uma categoria.
 */
@Getter
@Entity
@Builder
@Table(name = "item")
@Schema(description = "Entidade que representa um item dentro de uma categoria.")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "categoria")
public class Item {

    /**
     * Identificador único do item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único do item.", example = "1")
    private UUID id;

    /**
     * Descrição do item. Não pode ser nula.
     */
    @Column(nullable = false)
    @Schema(description = "Descrição do item.", example = "Enviar relatório mensal")
    private String descricao;

    /**
     * Indica se o item foi concluído.
     */
    @Column(nullable = false)
    @Builder.Default
    @Schema(description = "Indica se o item foi concluído.", example = "false")
    private boolean concluido = false;

    /**
     * Data de criação do item. Definida automaticamente na inserção.
     */
    @Column(nullable = false, updatable = false)
    @Schema(description = "Data de criação do item.", example = "2024-02-21T10:00:00")
    private LocalDateTime dataCriacao;

    /**
     * Data de atualização do item. Definida na atualização.
     */
    @Schema(description = "Data de criação do item.", example = "2024-02-21T10:00:00")
    private LocalDateTime dataAtualizacao;

    /**
     * Data limite para conclusão do item.
     */
    @Column
    @Schema(description = "Data limite para conclusão do item.", example = "2024-12-31T23:59:59")
    private LocalDateTime dataLimite;

    /**
     * Categoria à qual o item pertence.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @Schema(description = "Categoria associada ao item.")
    private Categoria categoria;

}
