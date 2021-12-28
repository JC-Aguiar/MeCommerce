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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**<h1>CONCEITO</h1>
 * TODO: descrever conceito e requisitos <br>
 *<h1>ATRIBUTOS </h1>
 * <b>tokenService:</b> classe que encapsula e descapsula tokens JWT <br>
 * <b>provedorLoginService:</b> lógica para identificar o usuário. <br>
 * <b>provedorAuth:</b> lógica para autenticar o usuário. <br>
 * <b>userService:</b> classe DAO para realziar o CRUD. <br>
 * @author JM Costal Aguiar
 */
@Controller
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	@Autowired private TokenService tokenService;
	@Autowired private ProvedorLoginService provedorLoginService;
	@Autowired private ProvedorAutorizadorService provedorAuth;
	@Autowired private UsuarioService userService;

	/**<hr>
	 * <h2>CONFIGURAR AUTORIZAÇÕES</h2>
	 * Configurando restrições de acesso aos end-points da API (1 ~ 6) <br>
	 * Configurando método de login personalizado (7) <br>
	 * <ol>
	 * <li><b> URLS RESTRITAS </b><br>
	 * 		authorizeRequests().mvcMatchers("/adm/**"): Configuração de URLs "/adm" <br>
	 * 		hasRole("ADMIN"): Define que somente usuários logados com perfil ADM tem acesso <br>
	 * </li>
	 * <li><b> URLS PRIVADAS </b><br>
	 * 		authorizeRequests().mvcMatchers("/profile/**"): URLs "/profile" só podem ser acessadas
	 * 		pelo pelo próprio usuário logado <br>
	 * 		hasRole("USER"): Define que somente usuários logados com perfil USER tem acesso <br>
	 * </li>
	 * <li> <b> URL LOGIN </b><br>
	 * 		authorizeRequests().mvcMatchers( HttpMethod.POST, "/login" ): Configuração de requisição
	 * 		POST para a URL "/login" <br>
	 * 		permitAll(): Define permissáo de acesso liberado a todos <br>
	 * </li>
	 * <li> <b>OUTRAS URLS </b><br>
	 * 		anyRequest(): Configuração para quaisqueres outras URLs <br>
	 * 		permitAll(): Define permissáo de acesso liberado a todos <br>
	 * </li>
	 * <li><b> INATVANDO CSRF </b><br>
	 * 		csrf().disable(): Método do Spring Security + método de desativação <br>
	 * </li>
	 * <li> <b> Aplicando API REST </b><br>
	 * 		sessionManagement(): Método responsável pela configuração das sessões (login) <br>
	 * 		sessionCreationPolicy():  Configurando política/estilo de sessão  <br>
	 * 		SessionCreationPolicy.STATELESS:  Definindo a API como REST <br>
	 * </li>
	 * <li> <b> Add Filtro póprio de login </b> <br>
	 *  	addFilterBefore(): Inserindo um filtro específico na cadeia padrão de filtros do Spring <br>
	 *  	filtroToken: Filtro personalizado a ser inserido (AutenticarTokenFilter) <br>
	 *  	filtroAntes: Filtro que será chamado depois (UsernamePasswordAuthenticationFilter) <br>
	 * </li>
	 * </ol>
	 * @param HttpSecurity automaticamente concedido pelo Spring.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		Class<UsernamePasswordAuthenticationFilter> filtroAntes = UsernamePasswordAuthenticationFilter.class;
		AutenticarTokenFilter filtroToken = new AutenticarTokenFilter(tokenService, userService);

		http
			.authorizeRequests().mvcMatchers("/adm/**").hasAnyRole("ADM").and()
			.authorizeRequests().mvcMatchers("/profile/**").hasAnyRole("USER").and()
			.authorizeRequests().mvcMatchers( HttpMethod.POST, "/login" ).permitAll()
			.anyRequest().permitAll().and()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.addFilterBefore(filtroToken, filtroAntes);
	}

	/**<hr>
	 * <h2>CONFIGURAR AUTENTICAÇÃO</h2>
	 * Método que configura e define autenticação:<br>
	 * 1. Definindo algoritmo de criptografia para senhas<br>
	 * 2. Definindo ProvedorLoginService como provedor de autenticação, através da interface UserDetailsService<br>
	 * @param AuthenticationManagerBuilder concedido automaticamente pelo Spring.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Classe de criptografia de senhas
		final BCryptPasswordEncoder ENCRIPT = new BCryptPasswordEncoder();
		//Definindo provedor de autenticação(1) e autorização(2)
		auth.userDetailsService(provedorLoginService).passwordEncoder(ENCRIPT)
				.and().authenticationProvider(provedorAuth);
	}

	/**<hr>
	 * <h2>PROVIDENCIANDO AUTHENTICATION MANAGER</h2>
	 * Método para criar Bean do Authentication Manager, necessário no LoginController <br>
	 * Por padrão o Spring não permite que o Authentication Manager seja uma dependência injetável <br>
	 */
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}

