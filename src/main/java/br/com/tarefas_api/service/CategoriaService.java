package br.com.tarefas_api.service;

import br.com.tarefas_api.domain.Categoria;
import br.com.tarefas_api.dto.CategoriaDTO;
import br.com.tarefas_api.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Serviço para gerenciamento de categorias.
 */
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Cria uma nova categoria.
     *
     * @param categoriaDTO DTO com os dados da categoria.
     * @return DTO da categoria criada.
     */
    @Transactional
    public CategoriaDTO criarCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return new CategoriaDTO(categoriaSalva.getNome());
    }

}
