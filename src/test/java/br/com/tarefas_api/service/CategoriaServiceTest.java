package br.com.tarefas_api.service;

import br.com.tarefas_api.domain.Categoria;
import br.com.tarefas_api.dto.CategoriaDTO;
import br.com.tarefas_api.exception.CategoriaComItensException;
import br.com.tarefas_api.exception.CategoriaJaExisteException;
import br.com.tarefas_api.exception.CategoriaNaoEncontradaException;
import br.com.tarefas_api.repository.CategoriaRepository;
import br.com.tarefas_api.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ItemRepository itemRepository;

    private Categoria categoria;

    private CategoriaDTO categoriaDTO;

    private UUID categoriaId;

    @BeforeEach
    void setUp() {
        categoriaId = UUID.randomUUID();

        categoria = Categoria.builder()
                .id(categoriaId)
                .nome("Trabalho")
                .build();

        categoriaDTO = new CategoriaDTO(UUID.randomUUID(), "Trabalho");
    }

    @Test
    void deveCriarCategoriaComSucesso() {
        when(categoriaRepository.findByNome("Trabalho")).thenReturn(Optional.empty());
        when(categoriaRepository.save(any())).thenReturn(categoria);

        CategoriaDTO resultado = categoriaService.criarCategoria(categoriaDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Trabalho");

        verify(categoriaRepository).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaJaExiste() {
        when(categoriaRepository.findByNome("Trabalho")).thenReturn(Optional.of(categoria));

        assertThatThrownBy(() -> categoriaService.criarCategoria(categoriaDTO))
                .isInstanceOf(CategoriaJaExisteException.class)
                .hasMessage("Já existe uma categoria com o nome: Trabalho");

        verify(categoriaRepository, never()).save(any());
    }

    @Test
    void deveListarTodasCategorias() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

        List<CategoriaDTO> resultado = categoriaService.listarCategorias();

        assertThat(resultado)
                .isNotEmpty()
                .hasSize(1)
                .extracting(CategoriaDTO::getNome)
                .containsExactly("Trabalho");
    }

    @Test
    void deveBuscarCategoriaPorIdComSucesso() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));

        CategoriaDTO resultado = categoriaService.buscarCategoriaPorId(categoriaId);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Trabalho");
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontrada() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.buscarCategoriaPorId(categoriaId))
                .isInstanceOf(CategoriaNaoEncontradaException.class)
                .hasMessage("Categoria não encontrada com ID: " + categoriaId);
    }

    @Test
    void deveAtualizarCategoriaComSucesso() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(itemRepository.existsByCategoriaId(categoriaId)).thenReturn(false);
        when(categoriaRepository.findByNome("Trabalho Atualizado")).thenReturn(Optional.empty());
        when(categoriaRepository.save(any())).thenReturn(categoria);

        CategoriaDTO atualizadoDTO = new CategoriaDTO(null, "Trabalho Atualizado");
        CategoriaDTO resultado = categoriaService.atualizarCategoria(categoriaId, atualizadoDTO);

        assertThat(resultado.getNome()).isEqualTo("Trabalho Atualizado");
        verify(categoriaRepository).save(any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarCategoriaInexistente() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.atualizarCategoria(categoriaId, categoriaDTO))
                .isInstanceOf(CategoriaNaoEncontradaException.class);
    }

    @Test
    void deveLancarExcecaoAoAtualizarCategoriaComItens() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(itemRepository.existsByCategoriaId(categoriaId)).thenReturn(true);

        assertThatThrownBy(() -> categoriaService.atualizarCategoria(categoriaId, categoriaDTO))
                .isInstanceOf(CategoriaComItensException.class);
    }

    @Test
    void deveExcluirCategoriaComSucesso() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(itemRepository.existsByCategoriaId(categoriaId)).thenReturn(false);

        categoriaService.excluirCategoria(categoriaId);

        verify(categoriaRepository).delete(categoria);
    }

    @Test
    void deveLancarExcecaoAoExcluirCategoriaInexistente() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.excluirCategoria(categoriaId))
                .isInstanceOf(CategoriaNaoEncontradaException.class);
    }

    @Test
    void deveLancarExcecaoAoExcluirCategoriaComItens() {
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(itemRepository.existsByCategoriaId(categoriaId)).thenReturn(true);

        assertThatThrownBy(() -> categoriaService.excluirCategoria(categoriaId))
                .isInstanceOf(CategoriaComItensException.class);
    }

}
