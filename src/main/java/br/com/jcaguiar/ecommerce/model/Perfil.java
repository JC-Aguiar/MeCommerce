package br.com.jcaguiar.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity(name = "perfil")
final public class Perfil extends EntidadeData<Integer> implements GrantedAuthority {
	
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Usuario usuario;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PerfilTipo nome;
	
	@Override
	public String getAuthority() {
		return this.nome.toString();
	}

}
