package br.com.tarefas_api.controller;

import br.com.tarefas_api.dto.ItemDTO;
import br.com.tarefas_api.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private ItemDTO itemDTO;

    @BeforeEach
    void setUp() {
        itemDTO = ItemDTO.builder()
                .categoriaId(1L)
                .descricao("Finalizar relatório")
                .concluido(false)
                .dataLimite(java.time.LocalDateTime.now())
                .categoriaId(1L)
                .build();
    }

    @Test
    void criarItem_DeveRetornarItemCriado() {
        when(itemService.criarItem(any(ItemDTO.class))).thenReturn(itemDTO);

        ResponseEntity<ItemDTO> response = itemController.criarItem(itemDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(itemDTO, response.getBody());
        verify(itemService, times(1)).criarItem(any(ItemDTO.class));
    }

    @Test
    void listarItens_DeveRetornarListaDeItens() {
        List<ItemDTO> itens = Collections.singletonList(itemDTO);
        when(itemService.listarItens()).thenReturn(itens);

        ResponseEntity<List<ItemDTO>> response = itemController.listarItens();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(itens, response.getBody());
        verify(itemService, times(1)).listarItens();
    }

    @Test
    void buscarItemPorId_DeveRetornarItem() {
        when(itemService.buscarPorId(1L)).thenReturn(itemDTO);

        ResponseEntity<ItemDTO> response = itemController.buscarItemPorId(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(itemDTO, response.getBody());
        verify(itemService, times(1)).buscarPorId(1L);
    }

    @Test
    void atualizarItem_DeveRetornarItemAtualizado() {
        when(itemService.atualizarItem(eq(1L), any(ItemDTO.class))).thenReturn(itemDTO);

        ResponseEntity<ItemDTO> response = itemController.atualizarItem(1L, itemDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(itemDTO, response.getBody());
        verify(itemService, times(1)).atualizarItem(eq(1L), any(ItemDTO.class));
    }

    @Test
    void deletarItem_DeveRetornarNoContent() {
        doNothing().when(itemService).removerItem(1L);

        ResponseEntity<Void> response = itemController.deletarItem(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(itemService, times(1)).removerItem(1L);
    }

}
