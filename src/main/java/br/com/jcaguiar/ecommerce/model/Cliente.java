package br.com.jcaguiar.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**<h1>CONCEITO</h1>
 * Cadastro completo de um usuário. <br>
 * Se alguém deseja de fato efetuar uma compra, precisará preencher esses atributos. <br>
 * Por isso que existe diferença entre usuários e clientes. Clientes já efetuar cadastro e já compraram algo. <br>
 * É impossível existir um cliente que não seja um usário, pois Cliente está dentro de Usuario.
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> identificador Integer do registro no banco de dados. <br>
 * <b>endereco:</b> lista de objetos Endereco responsáveis pelos possíveis locais de residência ou entrega do usuário. <br>
 * <b>pedido:</b> lista de objetos Pedido, histórico de compras desse cliente. <br>
 * <b>cartao:</b> lista de objetos Cartao, uma vez cadastrados ficam disponíveis para serem usados facilmente. <br>
 * <b>nome:</b> String que representa o primeiro nome do cliente.  <br>
 * <b>sobrenome:</b> String que representa o último nome do cliente. <br>
 * <b>cpf:</b> String de 11 dígitos identificador oficial federal. <br>
 * <b>phone:</b> String de no máximo 50 dígitos que representa o telefone do cliente. <br>
 * <b>data_nascimento:</b> data LocalDate do nascimento do cliente. <br>
 * <b>idade:</b> String  <br>
 * <b>sexo:</b>  <br>
 * <b>votos:</b>  <br>
 * @author João MC Aguair
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@PrimaryKeyJoinColumn(name="id")
@Entity(name = "cliente")
final public class Cliente extends Usuario {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(fetch = FetchType.LAZY)
	private final List<Endereco> endereco = new ArrayList<>();

	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedido;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private final List<Cartao> cartao = new ArrayList<>();

	private String nome;

	private String sobrenome;

	@Column(nullable = false, unique = true)
	private String cpf;

	@Column(length = 50)
	private String phone;

	private LocalDate data_nascimento;

	private short idade;

	private char sexo;

	private short votos;

}