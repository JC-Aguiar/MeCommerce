package br.com.jcaguiar.ecommerce.projection;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ErroMensagemGET implements MasterGET {

    Integer status;
    String mensagem;
    LocalDateTime data = DataFormato.now();

    public ErroMensagemGET(Integer status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }
}
