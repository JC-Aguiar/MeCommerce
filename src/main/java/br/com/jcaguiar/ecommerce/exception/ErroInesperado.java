package br.com.jcaguiar.ecommerce.exception;

public class ErroInesperado extends RuntimeException {

    Exception exception;

    public ErroInesperado(Exception exception) {
        this.exception = exception;
    }
}
