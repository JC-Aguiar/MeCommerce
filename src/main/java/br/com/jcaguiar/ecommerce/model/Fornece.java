package br.com.jcaguiar.ecommerce.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity(name = "fornece")
final public class Fornece extends EntidadeData<Integer> {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Produto produto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Fornecedor fornecedor;
	
}
