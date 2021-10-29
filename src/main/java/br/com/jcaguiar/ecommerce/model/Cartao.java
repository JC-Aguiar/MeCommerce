package br.com.jcaguiar.ecommerce.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private LocalDateTime data_validade;

	@Column(nullable = false)
	private String titular;

	@Column(nullable = false, length = 11)
	private String cpf;

	@Column(nullable = false)
	private String agencia;

}
