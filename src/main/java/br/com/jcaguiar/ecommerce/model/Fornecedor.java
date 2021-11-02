package br.com.jcaguiar.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**<h1>CONCEITO</h1>
 * Classe representando empresa fornecedora dos produtos. Possui um relacionamento N-N bidirecional com Produtos. <br>
 * [Produto] <-> [Fornece] <-> [Fornecedor] <br>
 * Maior parte de seus atributos estão na superclasse <b>Empresa</b> <br>
 * TODO: finalziar javadoc
 * <h1>ATRIBUTOS</h1>
 * <b>fornece:</b>  <br>
 * <b>endereco:</b>  <br>
 * @author JM Costal Aguiar
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "fornecedor")
final public class Fornecedor extends Empresa<Short> {
	
	@OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private final List<Fornece> fornece = new ArrayList<>();
	
	@OneToOne
	private Endereco endereco;

}
