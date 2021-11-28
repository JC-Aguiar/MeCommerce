package br.com.jcaguiar.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**<h1>CONCEITO</h1>
 * Classe abstrata destinada a agrupar os principais atributos das empresas. <br>
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> identificador (declarado pelas classes filhos) do registro no banco de dados.  <br>
 * <b>doc:</b> String obrigatória e única contendo o documento oficial. No Brasil é equivalente ao CNPJ. <br>
 * <b>razaoSocial:</b> String obrigatória contendo o nome oficial. No Brasil é equivalente á Razão Social. <br>
 * <b>nome:</b> String contendo o nome simplificado da empresa. No Brasil é equivalente ao Nome Fantasia. <br>
 * <b>ie:</b> String contendo o código distrital/municipal da empresa. <br>
 * <b>matriz:</b> se o cadastro é matriz, matriz = true. se cadastro é filial, matriz = false.  <br>
 * @author JM Costal Aguiar
 * @param <ID> tipo do atributo id que as classes filhos precisam declarar.
 *
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Empresa<ID> extends EntidadeData<ID> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ID id;

	@Column(nullable = false, unique = true)
	private String doc;

	@Column(nullable = false)
	private String razaoSocial;

	private String nome;

	private String ie;

	private Boolean matriz;

}
