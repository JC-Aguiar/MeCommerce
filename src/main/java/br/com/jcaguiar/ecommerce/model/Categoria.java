package br.com.jcaguiar.ecommerce.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "categoria")
final public class Categoria implements Entidade<Short> {
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Setor setor;

	@Column(unique = true, nullable = false)
	private String nome;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private final LocalDateTime data_cadastro = DataFormato.now();
}
