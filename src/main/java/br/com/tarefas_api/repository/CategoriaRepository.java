package br.com.tarefas_api.repository;

import br.com.tarefas_api.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório responsável pelas operações CRUD na entidade Categoria.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

    Optional<Categoria> findByNome(String nome);

}
