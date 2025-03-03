package br.com.tarefas_api.repository;

import br.com.tarefas_api.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório responsável pelas operações CRUD na entidade Item.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

}
