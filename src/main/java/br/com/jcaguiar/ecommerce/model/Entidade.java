package br.com.jcaguiar.ecommerce.model;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**<h1>CONCEITO</h1>
 * Interface simples exigindo que a classe aplicada terá um id e o disponibilizá seu get <br>
 * @author JM Costal Aguiar
 * @param <ID> tipo do atributo id que as classes implementadas devem declarar.
 */
@NoArgsConstructor
@SuperBuilder
public abstract class Entidade<ID> {

	public abstract ID getId();

	public Object getSelf() {
		return this;
	}

}
