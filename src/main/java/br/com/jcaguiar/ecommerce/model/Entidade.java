package br.com.jcaguiar.ecommerce.model;

/**<h1>CONCEITO</h1>
 * Interface simples exigindo que a classe aplicada terá um id e o disponibilizá seu get <br>
 * @author JM Costal Aguiar
 * @param <ID> tipo do atributo id que as classes implementadas devem declarar.
 */
public interface Entidade<ID> {

	public ID getId();

}
