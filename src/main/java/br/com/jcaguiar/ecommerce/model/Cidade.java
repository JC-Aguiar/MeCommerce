package br.com.jcaguiar.ecommerce.model;

import lombok.*;

import javax.persistence.*;

/**<h1>CONCEITO</h1>
 * Classe responsável por representar cidades. <br>
 * A escala completa de uma localização é: Pais > Estado > Cidade > Endereço <br>
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> identificador Integer do registro no banco de dados  <br>
 * <b>estado:</b> objeto Estado que representa o município em que se situa <br>
 * <b>nome:</b> String contendo o nome da cidade <br>
 * @author João MC Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "cidade")
final public class Cidade implements Entidade<Integer >{
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Estado estado;

	@Column(nullable = false)
	private String nome;
}
