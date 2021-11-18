package br.com.jcaguiar.ecommerce.exception;

import br.com.jcaguiar.ecommerce.dto.MasterPOST;
import br.com.jcaguiar.ecommerce.projection.MasterGET;
import br.com.jcaguiar.ecommerce.util.DataFormato;

import java.time.LocalDateTime;

public class ErroInesperadoException implements MasterGET, MasterPOST {

    int status;

    String titulo;

    String mensagem;

    LocalDateTime data = DataFormato.now();

    public ErroInesperadoException(int status, String titulo, String mensagem) {
        this.status = status;
        this.titulo = titulo;
        this.mensagem = mensagem;
    }

}


