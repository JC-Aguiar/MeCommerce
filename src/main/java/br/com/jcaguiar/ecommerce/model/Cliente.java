package br.com.jcaguiar.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity(name = "cliente")
final public class Cliente {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(mappedBy = "cliente")
	private Usuario usuario;

	@OneToOne
	private Endereco endereco;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")  //?
	private final List<Endereco> outrosEnderecos = new ArrayList<>();

	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedido;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private final List<Cartao> cartao = new ArrayList<>();

	private String nome;

	private String sobrenome;

	@Column(nullable = false, unique = true)
	private String cpf;

	private String phone;

	private LocalDate data_nascimento;

	private short idade;

	private char sexo;

	private short votos;

}