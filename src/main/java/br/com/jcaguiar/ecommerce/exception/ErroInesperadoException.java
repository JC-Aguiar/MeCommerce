package br.com.jcaguiar.ecommerce.exception;

import br.com.jcaguiar.ecommerce.dto.MasterPOST;
import br.com.jcaguiar.ecommerce.projection.MasterGET;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class ErroInesperadoException implements MasterGET, MasterPOST {

    Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() );
    int status;
    String error;
    String mensagem;
    String path;

    public ErroInesperadoException(HttpStatus http, String mensagem, String path) {
        this.status = http.value();
        this.error = http.getReasonPhrase();
        this.mensagem = mensagem;
        this.path = path;
    }

}


