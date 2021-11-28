package br.com.jcaguiar.ecommerce.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * TODO: finalizar javadoc
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "pagamento")
final public class Pagamento extends Entidade<Long> {

	/**
	 * TODO: como funciona um pagamento oficialmente? O objeto é criado e fica aguardando o comprovante,
	 * 		 para depois informar a data? Ou tudo deve ser criado atomicamente?
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pedido pedido;
	
	@Column(nullable = false)
	private String cartaoNumero;

	@Column(nullable = false)
	private LocalDate cartaoDataValidade;

	@Column(nullable = false)
	private String cartaoTitular;

	@Column(nullable = false, length = 11)
	private String cartaoCpf;

	@Column(nullable = false)
	private String cartaoAgencia;

	@Column(nullable = false)
	private String cartaoToken;

	@Column(nullable = false)
	private Byte parcelas;

	@Column(nullable = false)
	private BigInteger subtotal;

	private LocalDateTime data_pagamento;  //nullable?


	/**
	 * TODO: esse método ainda é necessário?
	 */
	public void setPagamento(@NotNull Cartao cartao, BigInteger subtotal, String token, Byte parcelas) {
		this.cartaoNumero = cartao.getNumero();
		this.cartaoDataValidade = cartao. getData_validade();
		this.cartaoTitular = cartao.getTitular();
		this.cartaoCpf = cartao.getCpf();
		this.cartaoAgencia = cartao.getAgencia();
		this.subtotal = subtotal;
		this.cartaoToken = token;
		this.parcelas = parcelas;
	}

}
