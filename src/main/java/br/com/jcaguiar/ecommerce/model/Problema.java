package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import br.com.jcaguiar.ecommerce.util.TratarString;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "problema")
final public class Problema implements Entidade<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 9000)
    String stack;

    String mensagem;

    LocalDateTime dataErro = DataFormato.now();

    public Problema(@NotNull Exception exception) {
        this.stack = TratarString.stackTraceToString(exception.getStackTrace());
        this.mensagem = exception.getMessage();
    }

}
