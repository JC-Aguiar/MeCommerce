package br.com.jcaguiar.ecommerce.util;

public enum ApiRespostaEnum {
    SUCESSO("Operação realizada com sucesso"),
    ERRO_PARCIAL("Existem alguns erros no processo"),
    ERRO_TOTAL("A operaçao não pode ser realizada");

    ApiRespostaEnum(String status) {}
}
