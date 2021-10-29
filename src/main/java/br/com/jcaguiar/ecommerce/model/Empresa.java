package br.com.jcaguiar.ecommerce.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Empresa<ID> extends EntidadeData<ID> {
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private ID id;

	@Column(nullable = false, unique = true)
	private String doc;

	@Column(nullable = false)
	private String razao_social;

	private String nome;

	private String ie;

	private boolean matriz;

}
