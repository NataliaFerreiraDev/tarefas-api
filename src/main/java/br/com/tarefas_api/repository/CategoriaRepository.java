package br.com.tarefas_api.repository;

import br.com.tarefas_api.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para acesso à entidade Categoria no banco de dados.
 */
public interface CategoriaRepository  extends JpaRepository<Categoria, Long> {

}
