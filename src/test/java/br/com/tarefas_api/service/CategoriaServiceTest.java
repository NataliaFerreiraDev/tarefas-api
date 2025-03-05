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

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Trabalho");

        categoriaDTO = new CategoriaDTO("Trabalho");
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
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        CategoriaDTO resultado = categoriaService.buscarCategoriaPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Trabalho");
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontrada() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.buscarCategoriaPorId(1L))
                .isInstanceOf(CategoriaNaoEncontradaException.class)
                .hasMessage("Categoria não encontrada com ID: 1");
    }

    @Test
    void deveAtualizarCategoriaComSucesso() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(itemRepository.existsByCategoriaId(1L)).thenReturn(false);
        when(categoriaRepository.findByNome("Trabalho Atualizado")).thenReturn(Optional.empty());
        when(categoriaRepository.save(any())).thenReturn(categoria);

        CategoriaDTO atualizadoDTO = new CategoriaDTO("Trabalho Atualizado");
        CategoriaDTO resultado = categoriaService.atualizarCategoria(1L, atualizadoDTO);

        assertThat(resultado.getNome()).isEqualTo("Trabalho Atualizado");
        verify(categoriaRepository).save(any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarCategoriaInexistente() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.atualizarCategoria(1L, categoriaDTO))
                .isInstanceOf(CategoriaNaoEncontradaException.class);
    }

    @Test
    void deveLancarExcecaoAoAtualizarCategoriaJaExistente() {
        Categoria outraCategoria = new Categoria();
        outraCategoria.setId(2L);
        outraCategoria.setNome("Outra");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(itemRepository.existsByCategoriaId(1L)).thenReturn(false);
        when(categoriaRepository.findByNome("Outra")).thenReturn(Optional.of(outraCategoria));

        CategoriaDTO novaCategoriaDTO = new CategoriaDTO("Outra");

        assertThatThrownBy(() -> categoriaService.atualizarCategoria(1L, novaCategoriaDTO))
                .isInstanceOf(CategoriaJaExisteException.class);
    }

    @Test
    void deveLancarExcecaoAoAtualizarCategoriaComItens() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(itemRepository.existsByCategoriaId(1L)).thenReturn(true);

        assertThatThrownBy(() -> categoriaService.atualizarCategoria(1L, categoriaDTO))
                .isInstanceOf(CategoriaComItensException.class);
    }

    @Test
    void deveExcluirCategoriaComSucesso() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(itemRepository.existsByCategoriaId(1L)).thenReturn(false);

        categoriaService.excluirCategoria(1L);

        verify(categoriaRepository).delete(categoria);
    }

    @Test
    void deveLancarExcecaoAoExcluirCategoriaInexistente() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.excluirCategoria(1L))
                .isInstanceOf(CategoriaNaoEncontradaException.class);
    }

    @Test
    void deveLancarExcecaoAoExcluirCategoriaComItens() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(itemRepository.existsByCategoriaId(1L)).thenReturn(true);

        assertThatThrownBy(() -> categoriaService.excluirCategoria(1L))
                .isInstanceOf(CategoriaComItensException.class);
    }

}
