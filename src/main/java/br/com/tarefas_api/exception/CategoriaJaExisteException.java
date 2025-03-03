package br.com.tarefas_api.exception;

public class CategoriaJaExisteException extends RuntimeException {
    public CategoriaJaExisteException(String nome) {
        super("Já existe uma categoria com o nome: " + nome);
    }
}