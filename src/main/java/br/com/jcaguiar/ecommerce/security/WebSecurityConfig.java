package br.com.jcaguiar.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;

import br.com.jcaguiar.ecommerce.service.UsuarioService;

@Controller
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/**CONCEITO
	 * 
	 */
	/**ATRIBUTOS
	 * 
	 */
	@Autowired private TokenService tokenService;
	@Autowired private ProvedorLoginService provedorLoginService;
	@Autowired private ProvedorAutorizarService provedorAuth;
	@Autowired private UsuarioService userService;
 
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/**CONFIGURAR AUTORIZAÇÕES
	 * Configurando restrições de acesso aos end-points da API (1 ~ 6)
	 * Configurando método de login personalizado (7)
	 * 1) URLs restritas
	 * 		> authorizeRequests().mvcMatchers("/adm/**"):
	 * 				Configuração de URLs "/adm"
	 * 		> hasRole("ADMIN"):
	 * 				Define que somente usuários logados com perfil ADM tem acesso
	 * 
	 * 2) URLs privados
	 * 		> authorizeRequests().mvcMatchers("/user/**"):
	 * 				URLs "/user" só podem ser acessadas pelo pelo próprio usuário logado
	 * 		> hasRole("USER"):
	 * 				Define que somente usuários logados com perfil USER tem acesso
	 * 
	 * 3) URL login
	 * 		> authorizeRequests().mvcMatchers( HttpMethod.POST, "/login" ):
	 * 				Configuração de requisição POST para a URL "/login"
	 * 		> permitAll():
	 * 				 Define permissáo de acesso liberado a todos
	 * 			
	 * 4) Outras URLs
	 * 		> anyRequest():
	 * 				Configuração para quaisqueres outras URLs
	 * 		> permitAll():
	 * 				 Define permissáo de acesso liberado a todos
	 * 
	 * 5) Inativando CSRF
	 * 		> csrf().disable():
	 * 				Método do Spring Security + método de desativação
	 * 
	 * 6) Aplicando API REST
	 * 		> sessionManagement():
	 * 				Método responsável pela configuração das sessões (login)
	 * 		> sessionCreationPolicy():
	 * 				Configurando política/estilo de sessão 
	 * 		> SessionCreationPolicy.STATELESS:
	 * 				Definindo a API como REST
	 * 
	 *  7) Add Filtro póprio de login
	 *  	> addFilterBefore():
	 *  			Inserindo um filtro específico na cadeia padrão de filtros do Spring
	 *  	> filtroToken:
	 *  			Filtro personalizado a ser inserido (AutenticarTokenFilter)
	 *  	> filtroAntes:
	 * 				Filtro que será chamado depois (UsernamePasswordAuthenticationFilter)
	 * 
	 * @param HttpSecurity
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		Class<UsernamePasswordAuthenticationFilter> filtroAntes = UsernamePasswordAuthenticationFilter.class;
		AutenticarTokenFilter filtroToken = new AutenticarTokenFilter(tokenService, userService);
		
		http
			.authorizeRequests().mvcMatchers("/adm/**").hasAnyRole("ADM").and()
			//.authorizeRequests().mvcMatchers("/user/**").hasAnyRole("USER").and() TODO: DESFAZER esse comentário
			.authorizeRequests().mvcMatchers( HttpMethod.POST, "/login" ).permitAll()
			.anyRequest().permitAll().and()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.addFilterBefore(filtroToken, filtroAntes);
	}
	
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/**CONFIGURAR AUTENTICAÇÃO
	 * Método que configura e define autenticação:
	 * 1) Definindo algoritmo de criptografia para senhas
	 * 2) Definindo ProvedorLoginService como provedor de autenticação, através da interface UserDetailsService
	 *  
	 * @param AuthenticationManagerBuilder
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Classe de criptografia de senhas 
		final BCryptPasswordEncoder ENCRIPT = new BCryptPasswordEncoder();
		
		//Definindo provedor de autenticação
		//Definindo provedor de autorização
		auth.userDetailsService(provedorLoginService).passwordEncoder(ENCRIPT).and()
			.authenticationProvider(provedorAuth);
	}
	
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/**PROVIDENCIANDO AUTHENTICATION MANAGER
	 * Método para criar Bean do Authentication Manager, necessãrio no LoginController
	 * Por padrão o Spring não permite que o Authentication Manager seja uma dependência injetãvel
	 */
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}

