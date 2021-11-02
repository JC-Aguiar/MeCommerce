package br.com.jcaguiar.ecommerce.model;

import lombok.*;

import javax.persistence.*;

/**<h1>CONCEITO</h1>
 * Classe que representa os estados de um país.<br>
 * A escala completa de uma localização é: Pais > Estado > Cidade > Endereço <br>
 * TODO: terminar javadoc
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b>  <br>
 * <b>pais:</b>  <br>
 * <b>nome:</b>  <br>
 * <b>sigla:</b>  <br>
 * @author JM Costal Aguiar
 */
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
