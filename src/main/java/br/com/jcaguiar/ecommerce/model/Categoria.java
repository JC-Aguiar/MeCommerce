package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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

	@Column(nullable = false)
	private final LocalDate data_cadastro = DataFormato.now().toLocalDate();
}
