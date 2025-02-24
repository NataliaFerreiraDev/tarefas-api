package br.com.tarefas_api.controller;

import br.com.tarefas_api.dto.CategoriaDTO;
import br.com.tarefas_api.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

}
