package br.com.tarefas_api.controller;

import br.com.tarefas_api.dto.ItemDTO;
import br.com.tarefas_api.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por expor os endpoints referentes aos itens da API REST.
 */
@RestController
@RequestMapping("/itens")
@Tag(name = "Itens", description = "API para gerenciamento de itens")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Cria um novo item.
     *
     * @param itemDTO DTO com os dados do item.
     * @return ResponseEntity com o DTO do item criado e status 201 (Created).
     */
    @PostMapping
    @Operation(summary = "Criar um novo item", description = "Cria e retorna um novo item cadastrado.")
    public ResponseEntity<ItemDTO> criarItem(@Valid @RequestBody ItemDTO itemDTO) {
        ItemDTO novoItem = itemService.criarItem(itemDTO);
        return ResponseEntity.ok(novoItem);
    }

    /**
     * Lista todos os itens.
     *
     * @return ResponseEntity com a lista de itens e status 200 (OK).
     */
    @GetMapping
    @Operation(summary = "Listar todos os itens", description = "Retorna todos os itens cadastrados.")
    public ResponseEntity<List<ItemDTO>> listarItens() {
        List<ItemDTO> itens = itemService.listarItens();
        return ResponseEntity.ok(itens);
    }

    /**
     * Busca um item pelo ID.
     *
     * @param id Identificador do item.
     * @return ResponseEntity com o DTO do item e status 200 (OK).
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID", description = "Retorna um item específico com base no ID informado.")
    public ResponseEntity<ItemDTO> buscarItemPorId(@PathVariable Long id) {
        ItemDTO item = itemService.buscarPorId(id);
        return ResponseEntity.ok(item);
    }

    /**
     * Atualiza um item existente pelo ID.
     *
     * @param id      Identificador do item a ser atualizado.
     * @param itemDTO DTO com os novos dados do item.
     * @return ResponseEntity com o DTO do item atualizado e status 200 (OK).
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item por ID", description = "Atualiza os dados de um item existente.")
    public ResponseEntity<ItemDTO> atualizarItem(@PathVariable Long id, @Valid @RequestBody ItemDTO itemDTO) {
        ItemDTO itemAtualizado = itemService.atualizarItem(id, itemDTO);
        return ResponseEntity.ok(itemAtualizado);
    }

    /**
     * Exclui um item pelo ID.
     *
     * @param id Identificador do item a ser excluído.
     * @return ResponseEntity com status 204 (No Content) em caso de sucesso.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir item por ID", description = "Remove um item existente pelo seu identificador.")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemService.removerItem(id);
        return ResponseEntity.noContent().build();
    }

}
