package br.com.tarefas_api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Representa uma categoria de itens na lista de tarefas.
 */
@Data
@Entity
@Table(name = "categoria")
@Schema(description = "Entidade que representa uma categoria de itens.")
public class Categoria {

    /**
     * Identificador único da categoria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da categoria.", example = "1")
    private Long id;

    /**
     * Nome da categoria. Deve ser único e não pode ser nulo.
     */
    @Column(unique = true, nullable = false, length = 100)
    @Schema(description = "Nome da categoria.", example = "Trabalho")
    private String nome;

    /**
     * Lista de itens associados a esta categoria.
     */
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "Lista de itens pertencentes a esta categoria.")
    private List<Item> itens;

}
