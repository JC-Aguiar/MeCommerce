package br.com.jcaguiar.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**<h1>CONCEITO</h1>
 * Cartões são guardados no banco de dados para facilitar a compra e melhorar a experiência do cliente. <br>
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> identificador Integer do registro no banco de dados <br>
 * <b>cliente:</b> objeto Cliente que utiliza esse cartão <br>
 * <b>numero:</b> String contendo os dígitos do cartão <br>
 * <b>data_validade:</b> data LocalDate da validade do cartão <br>
 * <b>titular:</b> String contendo o nome do titular do cartão <br>
 * <b>cpf:</b> String de 11 dígitos referente o CPF do titular do cartão <br>
 * <b>agencia:</b> String contendo o nome da agência doc artão <br>
 * @author João MC Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "cartao")
final public class Cartao extends Entidade<Integer> {

	/**
	 * TODO: os dados dos atributos "numero" e "cpf" devem ser criptografados
	 */
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@Column(unique = true, nullable = false, length = 20)
	private String numero;

	@Column(nullable = false)
	private LocalDate data_validade;

	@Column(nullable = false)
	private String titular;

	@Column(nullable = false, length = 11)
	private String cpf;

	@Column(nullable = false)
	private String agencia;

}
