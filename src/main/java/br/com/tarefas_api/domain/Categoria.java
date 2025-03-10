package br.com.tarefas_api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Representa uma categoria de itens na lista de tarefas.
 */
@Getter
@Builder
@Entity
@Table(name = "categoria")
@Schema(description = "Entidade que representa uma categoria.")
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    /**
     * Identificador único da categoria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único da categoria.", example = "1")
    private UUID id;

    /**
     * Nome da categoria. Deve ser único e não pode ser nulo.
     */
    @Column(unique = true, nullable = false, length = 100)
    @Schema(description = "Nome da categoria. Deve ser único.", example = "Trabalho")
    private String nome;

    /**
     * Lista de itens associados a esta categoria.
     * Relacionamento bidirecional mapeado pelo atributo 'categoria' em Item.
     * CascadeType.ALL garante que a exclusão de uma categoria remova os itens associados.
     * FetchType.LAZY evita carregamento desnecessário dos itens ao buscar categorias.
     */
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "Lista de itens pertencentes a esta categoria.")
    private List<Item> itens;

}
