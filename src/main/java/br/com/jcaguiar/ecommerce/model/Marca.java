package br.com.jcaguiar.ecommerce.model;

import javax.persistence.*;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "marca")
final public class Marca implements Entidade<Short> {
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;

	@Column(nullable = false, unique = true)
	private String nome;

	@Temporal(TemporalType.DATE)
	private LocalDateTime data_cadastro = DataFormato.now();
}
