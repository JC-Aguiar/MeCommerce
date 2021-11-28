package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * TODO: terminar javadoc
 *
 * @author JM Costal Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "voto")
final public class Voto extends Entidade<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;

	private final LocalDateTime data_voto = DataFormato.now();

}
