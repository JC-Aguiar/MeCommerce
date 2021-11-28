package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import br.com.jcaguiar.ecommerce.util.StringListJsonConverter;
import br.com.jcaguiar.ecommerce.util.TratarString;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "problema")
final public class Problema extends Entidade<Long> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 12000, nullable = false)
    @Convert(converter = StringListJsonConverter.class)
    List<String> stack;

    String mensagem;

    LocalDateTime dataErro = DataFormato.now();

    public Problema(@NotNull Exception exception) {
        this.stack = TratarString.arrayStackTrace( exception.getStackTrace() );
        this.mensagem = exception.getMessage();
    }

}
