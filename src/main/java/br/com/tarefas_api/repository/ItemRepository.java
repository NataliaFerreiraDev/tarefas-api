package br.com.tarefas_api.repository;

import br.com.tarefas_api.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para acesso à entidade Item no banco de dados.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

}
