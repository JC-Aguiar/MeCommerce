package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

/**<h1>CONCEITO</h1>
 * Classe que irá capturar e gravar qualquer acesso feito ao servidor. <br>
 * Esta classe é criada antes mesmo de se identificar o Usuário de fato,
 * então o mesmo deverá ser posteriormente inserido. <br>
 * A meta é vincular todos os IPs a Usuários já cadastrados para mapear pontos de acesso. <br>
 * realizar o vínculo automaticamente. <br>
 * <h1>ATRIBUTOS</h1>
 * <b>Id:</b> identificador Long desse registro no banco de dados <br>
 * <b>usuario:</b> objeto Usuario que efetuou o acesso <br>
 * <b>produto:</b> objeto Produto alvo do acesso <br>
 * <b>url:</b> url String alvo do acesso <br>
 * <b>data_acesso:</b> data LocalDateTime em que o acesso ocorreu <br>
 * <b>duracao:</b> duração Duration do processamento quando acessou <br>
 * <b>ip:</b> identificador de conexão String que realizou o acesso <br>
 * <b>browser:</b> navegador String que realizou o acesso <br>
 * <b>os:</b> sistema operacional String que realizou o acesso <br>
 * <b>hardware:</b> Enum do dispositivo de acesso: PC, NOTEBOOK, TABLET, SMARTPHONE ou OUTROS <br>
 * @author João MC Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@Entity(name = "acesso")
final public class Acesso extends Entidade<Long> {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;

	@Column(nullable = false) private String url;
	final private LocalDateTime data_acesso = DataFormato.now();

	private Duration duracao;

	@Column(nullable = false) private String ip;
	private String browser;

	private String os;

	private String hardware;


}
