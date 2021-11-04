package br.com.jcaguiar.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
/**
 * TODO: terminar javadoc
 *
 * @author JM Costal Aguiar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "produto_imagem")
final public class ProdutoImagem implements Entidade<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Produto produto;

	@Column(nullable = false) //TODO: no Deploy inserir "unique = true"
	private String imagem;

}
