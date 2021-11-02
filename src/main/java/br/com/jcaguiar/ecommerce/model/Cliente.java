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

/**<h1>CONCEITO</h1>
 * Cadastro completo de um usuário. <br>
 * Se alguém deseja de fato efetuar uma compra, precisará preencher esses atributos. <br>
 * Por isso que existe diferença entre usuários e clientes. Clientes já efetuar cadastro e já compraram algo. <br>
 * É impossível existir um cliente que não seja um usário, pois Cliente está dentro de Usuario.
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> identificador Integer do registro no banco de dados. <br>
 * <b>endereco:</b> lista de objetos Endereco, possíveis locais de residência ou entrega do cliente. <br>
 * 					Relacionamento unidimensional 1-N com atributo "endereco_id" <br>
 * 					Fetch lazy, Cascade all, OrphanRemoval on. <br>
 * <b>pedido:</b> lista de objetos Pedido, histórico de compras desse cliente. <br>
 * 				 	Relacionamento bidimensional 1-N entre os atributos "cliente_id" e "pedido_id" <br>
 * <b>cartao:</b> lista de objetos Cartao, uma vez cadastrados ficam disponíveis para serem usados facilmente. <br>
 * 					Relacionamento bidimensional 1-N com atributo "cartao_id" <br>
 * 					Fetch lazy, Cascade all, OrphanRemoval on. <br>
 * <b>nome:</b> String que representa o primeiro nome do cliente.  <br>
 * <b>sobrenome:</b> String que representa o último nome do cliente. <br>
 * <b>cpf:</b> String de 11 dígitos identificador oficial federal. Unique e Non-Null. <br>
 * <b>phone:</b> String de no máximo 50 dígitos que representa o telefone do cliente. <br>
 * <b>data_nascimento:</b> data LocalDate do nascimento do cliente. <br>
 * <b>idade:</b> dígitos Short que identifica a faixa etária do cliente <br>
 * <b>genero:</b> String contendo o sexo do cliente: MASCULINO, FEMININO ou TRANSEXUAL <br>
 * <b>votos:</b> quantidade Short de votos que o cliente já opinou no sistema <br>
 * @author João MC Aguair
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
@Entity(name = "cliente")
final public class Cliente extends Usuario {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany( cascade = CascadeType.ALL,
				fetch = FetchType.LAZY,
				orphanRemoval = true)
	@JoinColumn(name = "id")
	private final List<Endereco> endereco = new ArrayList<>();

	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedido;

	@OneToMany(	mappedBy = "cliente",
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY,
				orphanRemoval = true)
	private final List<Cartao> cartao = new ArrayList<>();

	private String nome;

	private String sobrenome;

	@Column(nullable = false, unique = true)
	private String cpf;

	@Column(length = 50)
	private String phone;

	private LocalDate data_nascimento;

	private Short idade;

	private GeneroEnum genero;

	private Short votos;

}