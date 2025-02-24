package br.com.tarefas_api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Representa um item dentro de uma categoria.
 */
@Data
@Entity
@Table(name = "item")
@Schema(description = "Entidade que representa um item dentro de uma categoria.")
public class Item {

    /**
     * Identificador único do item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do item.", example = "1")
    private Long id;

    /**
     * Descrição do item.
     */
    @Column(nullable = false)
    @Schema(description = "Descrição do item.", example = "Enviar relatório mensal")
    private String descricao;

    /**
     * Indica se o item foi concluído.
     */
    @Column(nullable = false)
    @Schema(description = "Indica se o item foi concluído.", example = "false")
    private Boolean concluido = false;

    /**
     * Data limite para conclusão do item.
     */
    @Column
    @Schema(description = "Data limite para conclusão do item.", example = "2024-12-31T23:59:59")
    private LocalDateTime dataLimite;

    /**
     * Data de criação do item. Definida automaticamente.
     */
    @Column(nullable = false, updatable = false)
    @Schema(description = "Data de criação do item.", example = "2024-02-21T10:00:00")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    /**
     * Categoria à qual o item pertence.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @Schema(description = "Categoria associada ao item.")
    private Categoria categoria;

}
