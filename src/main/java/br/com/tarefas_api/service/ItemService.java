package br.com.tarefas_api.service;

import br.com.tarefas_api.domain.Categoria;
import br.com.tarefas_api.domain.Item;
import br.com.tarefas_api.dto.ItemDTO;
import br.com.tarefas_api.exception.CategoriaNaoEncontradaException;
import br.com.tarefas_api.exception.ItemNaoEncontradoException;
import br.com.tarefas_api.repository.CategoriaRepository;
import br.com.tarefas_api.repository.ItemRepository;
import br.com.tarefas_api.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável pela lógica de negócios da entidade Item.
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoriaRepository categoriaRepository;

    public ItemService(ItemRepository itemRepository, CategoriaRepository categoriaRepository) {
        this.itemRepository = itemRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Cria um novo item.
     * @param itemDTO DTO contendo os dados do item.
     * @return DTO do item salvo.
     * @throws CategoriaNaoEncontradaException Se a categoria associada não for encontrada.
     */
    @Transactional
    public ItemDTO criarItem(ItemDTO itemDTO) {
        Categoria categoria = categoriaRepository.findById(itemDTO.getCategoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException(itemDTO.getCategoriaId()));

        Item salvo = itemRepository.save(converterParaEntidade(itemDTO, categoria));

        return converterParaDTO(salvo);
    }

    /**
     * Lista os itens de uma categoria específica.
     * @param idCategoria ID da categoria.
     * @return Lista de ItemDTOs.
     */
    public List<ItemDTO> listarItensDaCategoria(UUID idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(idCategoria));

        return itemRepository.findByCategoriaId(categoria.getId())
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    /**
     * Busca um item pelo ID.
     * @param id ID do item.
     * @return DTO do item encontrado.
     */
    public ItemDTO buscarPorId(UUID id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNaoEncontradoException(id));

        return converterParaDTO(item);
    }

    /**
     * Atualiza um item existente.
     * @param id ID do item a ser atualizado.
     * @param itemDTO DTO contendo os novos dados.
     * @return DTO do item atualizado.
     */
    @Transactional
    public ItemDTO atualizarItem(UUID id, ItemDTO itemDTO) {
        Item itemExistente = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNaoEncontradoException(id));

        Categoria categoria = categoriaRepository.findById(itemExistente.getCategoria().getId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException(itemDTO.getCategoriaId()));

        Item itemAtualizado = Item.builder()
                .id(itemExistente.getId())
                .descricao(itemDTO.getDescricao() != null ? itemDTO.getDescricao() : itemExistente.getDescricao())
                .concluido(itemDTO.isConcluido())
                .dataAtualizacao(LocalDateTime.now())
                .dataLimite(itemDTO.getDataLimite() != null ? DateUtils.parseDate(itemDTO.getDataLimite()) : itemExistente.getDataLimite())
                .categoria(categoria)
                .build();

        // Salva a entidade atualizada no banco
        Item salvo = itemRepository.save(itemAtualizado);

        // Retorna o DTO do item atualizado
        return converterParaDTO(salvo);
    }

    /**
     * Remove um item pelo ID.
     * @param id ID do item a ser removido.
     */
    @Transactional
    public void removerItem(UUID id) {
        if (!itemRepository.existsById(id)) {
            throw new ItemNaoEncontradoException(id);
        }
        itemRepository.deleteById(id);
    }

    /**
     * Converte um ItemDTO para a entidade Item.
     */
    private Item converterParaEntidade(ItemDTO itemDTO, Categoria categoria) {
        return Item.builder()
                .id(itemDTO.getId())
                .descricao(itemDTO.getDescricao())
                .dataCriacao(LocalDateTime.now())
                .concluido(itemDTO.isConcluido())
                .dataAtualizacao(DateUtils.parseDate(itemDTO.getDataAtualizacao()))
                .dataLimite(DateUtils.parseDate(itemDTO.getDataLimite()))
                .categoria(categoria)
                .build();
    }

    /**
     * Converte uma entidade Item para ItemDTO.
     */
    private ItemDTO converterParaDTO(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .descricao(item.getDescricao())
                .concluido(item.isConcluido())
                .dataCriacao(DateUtils.formatDate(item.getDataCriacao()))
                .dataAtualizacao(DateUtils.formatDate(item.getDataAtualizacao()))
                .dataLimite(DateUtils.formatDate(item.getDataLimite()))
                .categoriaId(item.getCategoria().getId())
                .build();
    }

}
