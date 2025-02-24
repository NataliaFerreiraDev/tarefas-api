package br.com.tarefas_api.repository;

import br.com.tarefas_api.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório responsável pelas operações CRUD na entidade Categoria.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
