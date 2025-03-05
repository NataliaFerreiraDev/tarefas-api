package br.com.tarefas_api.service;

import br.com.tarefas_api.domain.Categoria;
import br.com.tarefas_api.domain.Item;
import br.com.tarefas_api.dto.ItemDTO;
import br.com.tarefas_api.exception.CategoriaNaoEncontradaException;
import br.com.tarefas_api.exception.ItemNaoEncontradoException;
import br.com.tarefas_api.repository.CategoriaRepository;
import br.com.tarefas_api.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item;
    private ItemDTO itemDTO;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria(1L, "Trabalho", new ArrayList<>());
        item = Item.builder()
                .id(1L)
                .descricao("Finalizar relatório")
                .concluido(false)
                .dataLimite(LocalDate.of(2025, 12, 31).atStartOfDay())
                .categoria(categoria)
                .build();

        itemDTO = ItemDTO.builder()
                .descricao("Finalizar relatório")
                .concluido(false)
                .dataLimite(LocalDate.of(2025, 12, 31).atStartOfDay())
                .categoriaId(1L)
                .build();
    }

    @Test
    void criarItem_DeveCriarItemComSucesso() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDTO resultado = itemService.criarItem(itemDTO);

        assertNotNull(resultado);
        assertEquals(itemDTO.getDescricao(), resultado.getDescricao());
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void criarItem_DeveLancarExcecao_QuandoCategoriaNaoExiste() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoriaNaoEncontradaException.class, () -> itemService.criarItem(itemDTO));
    }

    @Test
    void atualizarItem_DeveAtualizarComSucesso() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDTO resultado = itemService.atualizarItem(1L, itemDTO);

        assertNotNull(resultado);
        assertEquals(itemDTO.getDescricao(), resultado.getDescricao());
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void atualizarItem_DeveLancarExcecao_QuandoItemNaoExiste() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ItemNaoEncontradoException.class, () -> itemService.atualizarItem(1L, itemDTO));
    }

    @Test
    void listarItens_DeveRetornarListaDeItens() {
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<ItemDTO> resultado = itemService.listarItens();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_DeveRetornarItem() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ItemDTO resultado = itemService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(item.getDescricao(), resultado.getDescricao());
    }

    @Test
    void buscarPorId_DeveLancarExcecao_QuandoItemNaoExiste() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ItemNaoEncontradoException.class, () -> itemService.buscarPorId(1L));
    }

    @Test
    void removerItem_DeveRemoverComSucesso() {
        when(itemRepository.existsById(1L)).thenReturn(true);
        doNothing().when(itemRepository).deleteById(1L);

        assertDoesNotThrow(() -> itemService.removerItem(1L));
        verify(itemRepository).deleteById(1L);
    }

    @Test
    void removerItem_DeveLancarExcecao_QuandoItemNaoExiste() {
        when(itemRepository.existsById(1L)).thenReturn(false);

        assertThrows(ItemNaoEncontradoException.class, () -> itemService.removerItem(1L));
    }

}
