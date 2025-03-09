package br.com.tarefas_api.repository;

import br.com.tarefas_api.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repositório responsável pelas operações CRUD na entidade Item.
 */
public interface ItemRepository extends JpaRepository<Item, UUID> {

    /**
     * Verifica se existe pelo menos um item associado a uma determinada categoria.
     * @param id ID da categoria.
     * @return true se existir pelo menos um item associado à categoria, caso contrário false.
     */
    boolean existsByCategoriaId(UUID id);

    /**
     * Retorna uma lista de itens associados a uma determinada categoria.
     * @param categoriaId ID da categoria.
     * @return Lista de itens associados à categoria fornecida.
     */
    List<Item> findByCategoriaId(UUID categoriaId);

}
