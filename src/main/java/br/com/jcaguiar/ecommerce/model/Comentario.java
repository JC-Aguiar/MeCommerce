package br.com.jcaguiar.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**<h1>CONCEITO</h1>
 * Classe responsável por armazenar mensagens e depoimentos de clientes sobre um produto mesmo produto. <br>
 * Somente Cliente pode anexar mensagens. Ou seja, usuários cadastrados. <br>
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> identificador Long do registro no banco de dados. <br>
 * <b>cliente:</b> objeto Cliente autor da mensagem. <br>
 * <b>produto:</b> objeto Produto alvo do comentário do cliente. Fetch lazy. <br>
 * <b>texto:</b> String contendo a mensagem. Fetch lazy. <br>
 * <b>reply:</b> identificador Long de resposta a outros comentários. Se a mensagem é um post independente, reply = -1.
 * Se a mensagem é uma resposta de outro comentário, reply = "comentario_id" da mensagem que faz referência. <br>
 * @author JM Costal Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity(name = "comentario")
final public class Comentario extends EntidadeData<Long> {
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;

	@Column(nullable = false)
	private String texto;

	private Long reply;
}
