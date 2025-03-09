package br.com.tarefas_api.controller;

import br.com.tarefas_api.dto.CategoriaDTO;
import br.com.tarefas_api.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaControllerTest {

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController categoriaController;

    private CategoriaDTO categoriaDTO;

    private UUID categoriaId;

    @BeforeEach
    void setUp() {
        categoriaDTO = new CategoriaDTO(UUID.randomUUID(), "Trabalho");
        categoriaId = UUID.randomUUID();
    }

    @Test
    void deveCriarCategoria() {
        when(categoriaService.criarCategoria(any(CategoriaDTO.class))).thenReturn(categoriaDTO);

        ResponseEntity<CategoriaDTO> response = categoriaController.criarCategoria(categoriaDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoriaDTO, response.getBody());
    }

    @Test
    void deveListarCategorias() {
        List<CategoriaDTO> categorias = Collections.singletonList(categoriaDTO);
        when(categoriaService.listarCategorias()).thenReturn(categorias);

        ResponseEntity<List<CategoriaDTO>> response = categoriaController.listarCategorias();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(categoriaDTO, response.getBody().getFirst());
    }

    @Test
    void deveBuscarCategoriaPorId() {
        when(categoriaService.buscarCategoriaPorId(categoriaId)).thenReturn(categoriaDTO);

        ResponseEntity<CategoriaDTO> response = categoriaController.buscarCategoriaPorId(categoriaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoriaDTO, response.getBody());
    }

    @Test
    void deveAtualizarCategoria() {
        when(categoriaService.atualizarCategoria(eq(categoriaId), any(CategoriaDTO.class))).thenReturn(categoriaDTO);

        ResponseEntity<CategoriaDTO> response = categoriaController.atualizarCategoria(categoriaId, categoriaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoriaDTO, response.getBody());
    }

    @Test
    void deveExcluirCategoria() {
        doNothing().when(categoriaService).excluirCategoria(categoriaId);

        ResponseEntity<Void> response = categoriaController.excluirCategoria(categoriaId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoriaService, times(1)).excluirCategoria(categoriaId);
    }

}
