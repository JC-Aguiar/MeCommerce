package br.com.jcaguiar.ecommerce.model;

import lombok.*;

import javax.persistence.*;

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
	private Pais pais;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String sigla;

}
