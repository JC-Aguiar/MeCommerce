package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * TODO: finalizar javadoc
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "marca")
final public class Marca extends Entidade<Short> {
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;

	@Column(nullable = false, unique = true)
	private String nome;

	private LocalDateTime data_cadastro = DataFormato.now();
}
