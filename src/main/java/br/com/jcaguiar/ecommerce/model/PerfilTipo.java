package br.com.jcaguiar.ecommerce.model;

public enum PerfilTipo {
    ADM("ROLE_ADM"), STAFF("ROLE_STAFF"), USER("ROLE_USER");
    private String tipo;

    //TODO: atributo "private" é necessário?
    private PerfilTipo(String tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return this.tipo;
    }
}
