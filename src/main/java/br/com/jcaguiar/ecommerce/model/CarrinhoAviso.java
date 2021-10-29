package br.com.jcaguiar.ecommerce.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "carrinho_aviso")
public class CarrinhoAviso extends EntidadeData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false) String aviso;

    @Temporal(TemporalType.TIME) LocalDateTime data_visto;
}
