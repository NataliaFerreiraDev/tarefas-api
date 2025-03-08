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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private ItemDTO itemDTO;

    private UUID itemId;

    private UUID categoriaId;

    @BeforeEach
    void setUp() {
        categoriaId = UUID.randomUUID();
        itemId = UUID.randomUUID();

        itemDTO = ItemDTO.builder()
                .descricao("Finalizar relatório")
                .concluido(false)
                .dataLimite(java.time.LocalDateTime.now())
                .categoriaId(categoriaId)
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
        when(itemService.buscarPorId(itemId)).thenReturn(itemDTO);

        ResponseEntity<ItemDTO> response = itemController.buscarItemPorId(itemId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(itemDTO, response.getBody());
        verify(itemService, times(1)).buscarPorId(itemId);
    }

    @Test
    void atualizarItem_DeveRetornarItemAtualizado() {
        when(itemService.atualizarItem(eq(itemId), any(ItemDTO.class))).thenReturn(itemDTO);

        ResponseEntity<ItemDTO> response = itemController.atualizarItem(itemId, itemDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(itemDTO, response.getBody());
        verify(itemService, times(1)).atualizarItem(eq(itemId), any(ItemDTO.class));
    }

    @Test
    void deletarItem_DeveRetornarNoContent() {
        doNothing().when(itemService).removerItem(itemId);

        ResponseEntity<Void> response = itemController.deletarItem(itemId);

        assertEquals(204, response.getStatusCode().value());
        verify(itemService, times(1)).removerItem(itemId);
    }

}
