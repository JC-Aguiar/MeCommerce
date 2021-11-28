package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**<h1>CONCEITO</h1>
 * Classe abstrata destinada a agrupar atributos de cadastro que necessitam de datas. <br>
 * <h1>ATRIBUTOS</h1>
 * <b>ativo:</b> Boolean. Se cadastro está acessível, ativo = true. Caso contrário, ativo = false. <br>
 * <b>data_cadastro:</b> data LocalDateTime em que o cadastro foi registrado no banco.
 * 						Por padrão recebe a data atual da criação. <br>
 * <b>data_ativo:</b> a última data LocalDateTime em que o cadastro foi alterado para ser visível.
 *  * 					Por padrão recebe a data atual da criação. <br>
 * <b>data_desativo:</b> a última data LocalDateTime em que o cadastro foi alterado para não ser visível. <br>
 * @author JM Costal Aguiar
 * @param <ID> tipo do atributo id que as classes filhos precisam declarar.
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class EntidadeData<ID> extends Entidade<ID> {
	
	protected Boolean ativo = false;

	protected LocalDateTime data_cadastro = DataFormato.now();

	protected LocalDateTime data_ativo;

	protected LocalDateTime data_desativo;

	/**<hr><h2>ATIVAR CADASTRO</h2>
	 * Ativa o cadastro para consultas e registra a data dessa operação
	 */
	public void ativar() {
		this.ativo = true;
		data_ativo = DataFormato.now();
	}

	/**<hr><h2>DESATIVAR CADASTRO</h2>
	 * Desativa o cadastro para consultas e registra a data dessa operação
	 */
	public void desativar() {
		this.ativo = false;
		data_desativo = DataFormato.now();
	}

	/**
	 * <hr><h2>RESETAR DATA CADASTRO</h2>
	 * Método que define data_cadastro para null. Utilizado para consultas por aproximação através da
	 * interface Exemple. <br>
	 */
	public void resetDataCadastro() {
		this.data_cadastro = null;
	}

	/**
	 * <hr><h2>RESETAR DATA ATIVO</h2>
	 * Método que define data_ativo para null. Utilizado para consultas por aproximação através da
	 * interface Exemple. <br>
	 */
	public void resetDataAtivo() {
		this.data_ativo = null;
	}

	/**
	 * <hr><h2>RESETAR DATA DESATIVO</h2>
	 * Método que define data_desatino para null. Utilizado para consultas por aproximação através da
	 * interface Exemple. <br>
	 */
	public void resetDataDesativo() {
		this.data_desativo = null;
	}

	/**
	 * <hr><h2>RESETAR DATAS</h2>
	 * Método que define todas as datas para null. Utilizado para consultas por aproximação através da
	 * interface Exemple. <br>
	 */
	public void resetDatas() {
		resetDataAtivo();
		resetDataDesativo();
		resetDataCadastro();
	}

}
