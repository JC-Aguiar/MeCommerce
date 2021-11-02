package br.com.jcaguiar.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**<h1>CONCEITO</h1>
 * Classe representando tabela intermediária para o relacionamento N-N bidirecional entre Produto e Fornecedor. <br>
 * [Produto] <-> [Fornece] <-> [Fornecedor] <br>
 * TODO: terminar javadoc
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b>  <br>
 * <b>produto:</b>  <br>
 * <b>fornecedor:</b>  <br>
 * @author JM Costal Aguiar
 */
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
