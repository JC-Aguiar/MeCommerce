package br.com.jcaguiar.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "cartao")
final public class Cartao implements Entidade<Integer> {
	
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
