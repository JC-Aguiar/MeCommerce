package br.com.jcaguiar.ecommerce.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class EntidadeData<ID> implements Entidade<ID> {
	
	protected boolean ativo = false;

	@Temporal(TemporalType.TIME) protected LocalDateTime data_cadastro = DataFormato.now();

	@Temporal(TemporalType.TIME) protected LocalDateTime data_ativo = DataFormato.now();

	@Temporal(TemporalType.TIME) protected LocalDateTime data_desativo;
	
	public void resetDataCadastro() {
		this.data_cadastro = null;
	}
	
	public void resetDataAtivo() {
		this.data_ativo = null;
	}
	
	public void resetDataDesativo() {
		this.data_desativo = null;
	}
	
	public void resetDatas() {
		resetDataAtivo();
		resetDataDesativo();
		resetDataCadastro();
	}

}
