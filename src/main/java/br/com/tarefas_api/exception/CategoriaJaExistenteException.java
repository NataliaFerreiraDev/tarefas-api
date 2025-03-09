package br.com.tarefas_api.exception;

public class CategoriaJaExistenteException extends CategoriaException {

    public CategoriaJaExistenteException(String nome) {
        super("Já existe uma categoria com o nome: " + nome);
    }

}
