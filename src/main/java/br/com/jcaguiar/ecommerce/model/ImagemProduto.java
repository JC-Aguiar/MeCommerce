package br.com.jcaguiar.ecommerce.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "produto_imagem")
final public class ImagemProduto implements Entidade<Long> {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@Column(nullable = false)
	private Produto produto;

	@Column(nullable = false, unique = true)
	private String imagem;

}
