package br.com.jcaguiar.ecommerce.model;

import lombok.*;

import javax.persistence.*;

/**<h1>CONCEITO</h1>
 * Classe responsável em identificar a exata localização das entregas dos produtos. <br>
 * A escala completa de uma localização é: Pais > Estado > Cidade > Endereço <br>
 * Um endereço possui a seguinte hierarquia: Bairro > Rua > Numero <br>
 * Apesar dessa hierarquia, o atributo CEP é o mais importante, pois pode preencher Bairro e Rua. <br>
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> identificador Integer do registro no banco de dados. <br>
 * <b>cidade:</b> objeto Cidade no qual o endereço se encontra. Fetch lazy. <br>
 * <b>cep:</b> String obrigatória que identificada o código postal. <br>
 * <b>rua:</b> String obrigatória que contem o nome da rua. <br>
 * <b>numero:</b> String obrigatória que informa o número do local na rua. <br>
 * <b>complemento:</b> String com informações adicionais. <br>
 * <b>bairro:</b> String obrigatória contendo a região no município. <br>
 * @author JM Costal Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "endereco")
final public class Endereco implements Entidade<Integer> {
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Cidade cidade;

	@Column(nullable = false)
	private String cep;

	@Column(nullable = false)
	private String rua;

	@Column(nullable = false)
	private String numero;

	private String complemento;

	@Column(nullable = false)
	private String bairro;

}
