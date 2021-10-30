package br.com.jcaguiar.ecommerce.model;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@Entity(name = "**/acesso")
final public class Acesso implements Entidade<Long> {
	/**CONCEITO
	 * Classe que irá capturar e gravar qualquer acesso feito ao servidor
	 * Esta classe é criada antes mesmo de se identificar o Usuário de fato,
	 * então o mesmo deverá ser posteriormente inserido.
	 * Se identificado Canal compatível com algum já utilizado por um Usuário anteriormente
	 * realizar o vínculo automaticamente
	 */

	/**TODO: CLASSE DISPOSITIVO
	 * Classe intermediária (ou bi-direcional) entre Acesso e Usuário
	 * Atributos:
	 * 		IP
	 * 		NAVEGADOR
	 * 		OS
	 * 		TIPO
	 */

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;
	private String url;
	final private LocalDateTime data_acesso = LocalDateTime.now();
	private Duration duracao;

	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/**REPORTAR RELATÓRIO
	 * Retorna texto dos atributos de acesso no padrão < Console.Log >
	 * @return String
	 */
	public String report() {
		return String.format(
				"<FILTRO DE ACESSOS> \n"
						+ "\tACESSO:   %s\n"
						+ "\tUSER:     %s\n"
						+ "\tURL:      %s\n"
						+ "\tDURAÇÃO:  %d.%ds\n"
						+ "</FILTRO DE ACESSOS> \n",
				DataFormato.formatar(data_acesso), usuario.getEmail(), url, duracao.getSeconds(), duracao.getNano());
	}

}