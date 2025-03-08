package br.com.tarefas_api.repository;

import br.com.tarefas_api.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repositório responsável pelas operações CRUD na entidade Item.
 */
public interface ItemRepository extends JpaRepository<Item, UUID> {

    /**
     * Verifica se existe pelo menos um item associado a uma determinada categoria.
     */
    boolean existsByCategoriaId(UUID id);

}
