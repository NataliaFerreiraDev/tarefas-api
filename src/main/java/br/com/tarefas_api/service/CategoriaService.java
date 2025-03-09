package br.com.tarefas_api.service;

import br.com.tarefas_api.domain.Categoria;
import br.com.tarefas_api.dto.CategoriaDTO;
import br.com.tarefas_api.exception.CategoriaComItensException;
import br.com.tarefas_api.exception.CategoriaJaExistenteException;
import br.com.tarefas_api.exception.CategoriaNaoEncontradaException;
import br.com.tarefas_api.repository.CategoriaRepository;
import br.com.tarefas_api.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
     * Cria uma nova categoria, garantindo que o nome seja único.
     *
     * @param categoriaDTO DTO com os dados da categoria.
     * @return DTO da categoria criada.
     */
    @Transactional
    public CategoriaDTO criarCategoria(CategoriaDTO categoriaDTO) {
        validarNomeUnico(categoriaDTO.getNome());

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
    public CategoriaDTO buscarCategoriaPorId(UUID id) {
        Categoria categoria = buscarCategoria(id);
        return converterParaDTO(categoria);
    }

    /**
     * Atualiza uma categoria existente pelo ID, garantindo nome único.
     * Só pode ser alterada se **não houver itens associados**.
     *
     * @param id ID da categoria.
     * @param categoriaDTO Dados para atualização.
     * @return DTO da categoria atualizada.
     */
    @Transactional
    public CategoriaDTO atualizarCategoria(UUID id, CategoriaDTO categoriaDTO) {
        Categoria categoriaExistente = buscarCategoria(id);

        validarCategoriaSemItens(id);
        validarNomeUnico(categoriaDTO.getNome());

        Categoria categoriaAtualizada = Categoria.builder()
                .id(categoriaExistente.getId())
                .nome(categoriaDTO.getNome())
                .build();

        categoriaRepository.save(categoriaAtualizada);

        return converterParaDTO(categoriaAtualizada);
    }

    /**
     * Exclui uma categoria pelo ID.
     * Só pode ser excluída se **não houver itens associados**.
     *
     * @param id ID da categoria a ser removida.
     */
    @Transactional
    public void excluirCategoria(UUID id) {
        Categoria categoria = buscarCategoria(id);
        validarCategoriaSemItens(id);

        categoriaRepository.delete(categoria);
    }

    /**
     * Verifica se já existe uma categoria com o mesmo nome.
     */
    private void validarNomeUnico(String nome) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findByNome(nome);
        if (categoriaExistente.isPresent()) {
            throw new CategoriaJaExistenteException(nome);
        }
    }

    /**
     * Busca uma categoria pelo ID e lança exceção se não for encontrada.
     */
    private Categoria buscarCategoria(UUID id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
    }

    /**
     * Verifica se a categoria possui itens associados e lança exceção, se necessário.
     */
    private void validarCategoriaSemItens(UUID id) {
        if (itemRepository.existsByCategoriaId(id)) {
            throw new CategoriaComItensException(id);
        }
    }

    /**
     * Converte um DTO para a entidade Categoria.
     */
    private Categoria converterParaEntidade(CategoriaDTO dto) {
        return Categoria.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .build();
    }

    /**
     * Converte uma entidade Categoria para DTO.
     */
    private CategoriaDTO converterParaDTO(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .build();
    }

}
