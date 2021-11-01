package br.com.jcaguiar.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "usuario")
public class Usuario extends EntidadeData<Integer> implements UserDetails {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Perfil> perfil;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Cliente cliente;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Carrinho carrinho;

	private static final long serialVersionUID = 1L; //TODO: pra que serve essa variável?

	@Column(unique=true, nullable=false)
	private String email;

	@Column(nullable=false)
	private String senha;

	private boolean empresa = false;

	private String foto;

	@Override
	public List<Perfil> getAuthorities() {
		return this.perfil;
	}
	
	public String getAuthoritiesToString() {
		List<String> perfis = new ArrayList<>();
		this.perfil.forEach(perfil -> {
			perfis.add( perfil.getAuthority() );
		});
		return perfis.toString();
	}
	
	@Override
	public String getPassword() {
		return this.senha;
	}
	
	@Override
	public String getUsername() {
		return this.email;
	}

	//TODO: MÉTODO NÃO DESENVOLVIDO. CONTA NÃO EXPIRTADA
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//TODO: MÉTODO NÃO DESENVOLVIDO. CONTA NÃO BLOQUEADA
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//TODO: MÉTODO NÃO DESENVOLVIDO. CREDENCIAIS ATUAIS EXPIRADAS
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
		
	@Override
	public boolean isEnabled() {
		return this.ativo;
	}

}
