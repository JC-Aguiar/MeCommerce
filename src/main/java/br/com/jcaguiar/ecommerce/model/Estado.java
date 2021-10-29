package br.com.jcaguiar.ecommerce.model;

import javax.persistence.*;

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
@Entity(name = "estado")
final public class Estado implements Entidade<Short> {
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Column(nullable = false)
	private Pais pais;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String sigla;

}
