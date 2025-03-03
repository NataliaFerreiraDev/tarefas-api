package br.com.tarefas_api.service;

import br.com.tarefas_api.domain.Categoria;
import br.com.tarefas_api.dto.CategoriaDTO;
import br.com.tarefas_api.exception.CategoriaComItensException;
import br.com.tarefas_api.exception.CategoriaNaoEncontradaException;
import br.com.tarefas_api.repository.CategoriaRepository;
import br.com.tarefas_api.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço para gerenciamento de categorias.
 */
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ItemRepository itemRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ItemRepository itemRepository) {
        this.categoriaRepository = categoriaRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Cria uma nova categoria.
     *
     * @param categoriaDTO DTO com os dados da categoria.
     * @return DTO da categoria criada.
     */
    @Transactional
    public CategoriaDTO criarCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = converterParaEntidade(categoriaDTO);
        Categoria salva = categoriaRepository.save(categoria);
        return converterParaDTO(salva);
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
                .map(this::converterParaDTO)
                .toList();
    }

    /**
     * Busca uma categoria pelo ID.
     *
     * @param id Identificador da categoria.
     * @return DTO da categoria encontrada.
     */
    @Transactional(readOnly = true)
    public CategoriaDTO buscarCategoriaPorId(Long id) {
        Categoria categoria = buscarCategoria(id);
        return converterParaDTO(categoria);
    }

    /**
     * Atualiza uma categoria existente pelo ID.
     * Só pode ser alterada se **não houver itens associados**.
     *
     * @param id ID da categoria.
     * @param categoriaDTO Dados para atualização.
     * @return DTO da categoria atualizada.
     */
    @Transactional
    public CategoriaDTO atualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = buscarCategoria(id);
        validarCategoriaSemItens(id);

        categoria.setNome(categoriaDTO.getNome());
        Categoria atualizada = categoriaRepository.save(categoria);

        return converterParaDTO(atualizada);
    }

    /**
     * Exclui uma categoria pelo ID.
     * Só pode ser excluída se **não houver itens associados**.
     *
     * @param id ID da categoria a ser removida.
     */
    @Transactional
    public void excluirCategoria(Long id) {
        Categoria categoria = buscarCategoria(id);
        validarCategoriaSemItens(id);

        categoriaRepository.delete(categoria);
    }

    /**
     * Busca uma categoria pelo ID e lança exceção se não for encontrada.
     */
    private Categoria buscarCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
    }

    /**
     * Verifica se a categoria possui itens associados e lança exceção, se necessário.
     */
    private void validarCategoriaSemItens(Long id) {
        if (itemRepository.existsByCategoriaId(id)) {
            throw new CategoriaComItensException(id);
        }
    }

    /**
     * Converte um DTO para a entidade Categoria.
     */
    private Categoria converterParaEntidade(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        return categoria;
    }

    /**
     * Converte uma entidade Categoria para DTO.
     */
    private CategoriaDTO converterParaDTO(Categoria categoria) {
        return new CategoriaDTO(categoria.getNome());
    }

}
