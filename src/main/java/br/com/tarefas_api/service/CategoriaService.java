package br.com.tarefas_api.service;

import br.com.tarefas_api.domain.Categoria;
import br.com.tarefas_api.dto.CategoriaDTO;
import br.com.tarefas_api.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Serviço para gerenciamento de categorias.
 */
@Service
public class CategoriaService {

    private static final String CATEGORIA_NAO_ENCONTRADA = "Categoria não encontrada com ID: ";

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

    /**
     * Lista todas as categorias cadastradas.
     *
     * @return Lista de DTOs das categorias.
     */
    @Transactional(readOnly = true)
    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> new CategoriaDTO(categoria.getNome()))
                .toList();
    }

    /**
     * Busca uma categoria pelo ID.
     *
     * @param id Identificador da categoria.
     * @return DTO da categoria encontrada.
     * @throws RuntimeException Se a categoria não for encontrada.
     */
    @Transactional(readOnly = true)
    public CategoriaDTO buscarCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CATEGORIA_NAO_ENCONTRADA + id));

        return new CategoriaDTO(categoria.getNome());
    }

    /**
     * Atualiza uma categoria existente pelo ID.
     *
     * @param id           Identificador da categoria a ser atualizada.
     * @param categoriaDTO DTO com os novos dados da categoria.
     * @return DTO da categoria atualizada.
     * @throws RuntimeException Se a categoria não for encontrada.
     */
    @Transactional
    public CategoriaDTO atualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CATEGORIA_NAO_ENCONTRADA + id));

        categoria.setNome(categoriaDTO.getNome());
        Categoria categoriaAtualizada = categoriaRepository.save(categoria);

        return new CategoriaDTO(categoriaAtualizada.getNome());
    }

    /**
     * Exclui uma categoria pelo ID.
     *
     * @param id Identificador da categoria a ser excluída.
     * @throws RuntimeException Se a categoria não for encontrada.
     */
    @Transactional
    public void excluirCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CATEGORIA_NAO_ENCONTRADA + id));

        categoriaRepository.delete(categoria);
    }

}
