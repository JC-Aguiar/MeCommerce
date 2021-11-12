package br.com.jcaguiar.ecommerce.exception;

import br.com.jcaguiar.ecommerce.model.Entidade;
import br.com.jcaguiar.ecommerce.projection.ErroMensagemGET;
import br.com.jcaguiar.ecommerce.util.DataFormato;
import br.com.jcaguiar.ecommerce.util.TratarString;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "erro_mensagem")
final public class ErroMensagem implements Entidade<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String stack;

    String classe;

    String log;

    LocalDateTime dataErro = DataFormato.now();

    public  ErroMensagem() {}

    public ErroMensagem(Class classe, @NotNull Exception exception) {
        this.stack = TratarString.stackTraceToString( exception.getStackTrace() );
        this.classe = classe.getName().toString();
        this.log = classe.getName().toString();
    }

    public ErroMensagemGET
}
