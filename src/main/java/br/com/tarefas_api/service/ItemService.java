package br.com.tarefas_api.service;

import br.com.tarefas_api.domain.Categoria;
import br.com.tarefas_api.domain.Item;
import br.com.tarefas_api.dto.ItemDTO;
import br.com.tarefas_api.exception.CategoriaNaoEncontradaException;
import br.com.tarefas_api.exception.ItemNaoEncontradoException;
import br.com.tarefas_api.repository.CategoriaRepository;
import br.com.tarefas_api.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Item item = converterParaEntidade(itemDTO, categoria);
        Item salvo = itemRepository.save(item);

        return converterParaDTO(salvo);
    }

    /**
     * Atualiza um item existente.
     * @param id ID do item a ser atualizado.
     * @param itemDTO DTO contendo os novos dados.
     * @return DTO do item atualizado.
     */
    @Transactional
    public ItemDTO atualizarItem(UUID id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNaoEncontradoException(id));

        Categoria categoria = categoriaRepository.findById(itemDTO.getCategoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException(itemDTO.getCategoriaId()));

        Item itemAtualizado = converterParaEntidade(itemDTO, categoria);
        itemAtualizado.setId(item.getId());
        itemAtualizado.setDataCriacao(item.getDataCriacao());

        Item salvo = itemRepository.save(itemAtualizado);

        return converterParaDTO(salvo);
    }

    /**
     * Lista todos os itens.
     * @return Lista de ItemDTOs.
     */
    public List<ItemDTO> listarItens() {
        return itemRepository.findAll()
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
                .descricao(itemDTO.getDescricao())
                .concluido(itemDTO.getConcluido())
                .dataLimite(itemDTO.getDataLimite())
                .categoria(categoria)
                .build();
    }

    /**
     * Converte uma entidade Item para ItemDTO.
     */
    private ItemDTO converterParaDTO(Item item) {
        return ItemDTO.builder()
                .descricao(item.getDescricao())
                .concluido(item.getConcluido())
                .dataLimite(item.getDataLimite())
                .categoriaId(item.getCategoria().getId())
                .build();
    }

}
