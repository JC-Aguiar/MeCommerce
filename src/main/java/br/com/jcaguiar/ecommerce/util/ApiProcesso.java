package br.com.jcaguiar.ecommerce.util;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiProcesso<T> {

    /**
     *  A cadamada Controller recebe o arquivo csv e o encaminha para a camada Service.
     *  O Service irá tratar cada linha de uma vez através da classe ApiProcesso.
     *  O objetivo é converter, validar, coletar ou criar e atribuir cada campo do csv para
     *  a entidade que queremos persistir no banco de dados.
     *  No caso de Produtos, durante o processo possivelmente iram persistir outras entidades associadas.
     *  Já que essas entidades perifericas podem retornar erro, existirão 2 métodos:
     *      1. ABSOLUTO: A entidade obtida no tratamento dessa linha csv será persistida invariavelmente;
     *      2. RELATIVO: A entidade obtida no tratamento dessa linha csv será descartada;
     *  O CsvProduto irá retornar um objeto ApiProcesso contendo:
     *      Objeto Produto;
     *      Lista das Marcas;
     *      Lista
     *      Lista das linhas com erro, com o exato campo errado;
     *  O Service irá retornar:
     */

    boolean erro;
    T objeto;
    String causa;
    Throwable exception;

    public Optional<T> getObjeto() {
        return Optional.of(this.objeto);
    }

    public Optional<Throwable> getException() {
        return Optional.of(this.exception);
    }

}
