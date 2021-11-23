package br.com.jcaguiar.ecommerce.exception;

public class ServerSideException extends RuntimeException {

    public ServerSideException(String mensagem) {
        super(mensagem);
    }

}
