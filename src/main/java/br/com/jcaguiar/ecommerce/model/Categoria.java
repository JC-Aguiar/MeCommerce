package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**<h1>CONCEITO</h1>
 * Para melhor organizar os Produtos, existem Setores e Categorias. Setores podem ter várias Categorias. <br>
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> identificador Short do registro no banco de dados <br>
 * <b>setor:</b> objeto Setor no qual essa categoria esrtá agrupada <br>
 * <b>nome:</b> String contendo o nome da categoria. Nomes iguais podem existir em setores diferentes. <br>
 * <b>data_cadastro:</b> LocalDate de quando a categoria foi criada  <br>
 * @author João MC Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "categoria")
final public class Categoria implements Entidade<Short> {

	/**
	 * TODO: aprender como criar chaves compostas. Embora a chave primária seja o id, nome deve ser único
	 * 		somente quando no mesmo setor. Nomes iguais podem existir em setores diferentes.
	 */
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Setor setor;

	@Column(unique = true, nullable = false)
	private String nome;

	@Column(nullable = false)
	private final LocalDate data_cadastro = DataFormato.now().toLocalDate();
}
