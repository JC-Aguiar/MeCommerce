package br.com.jcaguiar.ecommerce.exception;

import lombok.Getter;

@Getter
public class ClientSideException extends RuntimeException {

    public ClientSideException(String mensagem) {
        super(mensagem);
    }

    public ClientSideException(String mensagem,  Throwable cause) {
        super(mensagem, cause);
    }

}
