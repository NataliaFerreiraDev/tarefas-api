package br.com.tarefas_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe global para tratamento de exceções da API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções específicas de categoria.
     *
     * @param ex Exceção capturada.
     * @return ResponseEntity com detalhes do erro e status apropriado.
     */
    @ExceptionHandler(CategoriaException.class)
    public ResponseEntity<Object> handleCategoriaException(CategoriaException ex) {
        HttpStatus status = (ex instanceof CategoriaNaoEncontradaException) ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", "Erro de Categoria");
        body.put("message", ex.getMessage());

        return ResponseEntity.status(status).body(body);
    }

}
