package br.com.tarefas_api.controller;

import br.com.tarefas_api.dto.CategoriaDTO;
import br.com.tarefas_api.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controlador responsável por expor os endpoints referentes à categoria da API REST.
 */
@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "API para gerenciamento de categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Cria uma nova categoria.
     *
     * @param categoriaDTO DTO com os dados da categoria.
     * @return ResponseEntity com o DTO da categoria criada e status 201 (Created).
     */
    @PostMapping
    @Operation(summary = "Criar uma nova categoria", description = "Cria e retorna uma nova categoria cadastrada.")
    public ResponseEntity<CategoriaDTO> criarCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO novaCategoria = categoriaService.criarCategoria(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    /**
     * Lista todas as categorias.
     *
     * @return ResponseEntity com a lista de categorias e status 200 (OK).
     */
    @GetMapping
    @Operation(summary = "Listar todas as categorias", description = "Retorna todas as categorias cadastradas.")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        List<CategoriaDTO> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    /**
     * Busca uma categoria pelo ID.
     *
     * @param id Identificador da categoria.
     * @return ResponseEntity com o DTO da categoria e status 200 (OK).
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria específica com base no ID informado.")
    public ResponseEntity<CategoriaDTO> buscarCategoriaPorId(@PathVariable Long id) {
        CategoriaDTO categoriaDTO = categoriaService.buscarCategoriaPorId(id);
        return ResponseEntity.ok(categoriaDTO);
    }

    /**
     * Atualiza uma categoria existente pelo ID.
     *
     * @param id           Identificador da categoria a ser atualizada.
     * @param categoriaDTO DTO com os novos dados da categoria.
     * @return ResponseEntity com o DTO da categoria atualizada e status 200 (OK).
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria por ID", description = "Atualiza os dados de uma categoria existente.")
    public ResponseEntity<CategoriaDTO> atualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaAtualizada = categoriaService.atualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    /**
     * Exclui uma categoria pelo ID.
     *
     * @param id Identificador da categoria a ser excluída.
     * @return ResponseEntity com status 204 (No Content) em caso de sucesso.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir categoria por ID", description = "Remove uma categoria existente pelo seu identificador.")
    public ResponseEntity<Void> excluirCategoria(@PathVariable Long id) {
        categoriaService.excluirCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
