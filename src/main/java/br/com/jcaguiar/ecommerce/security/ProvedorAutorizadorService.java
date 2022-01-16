package br.com.jcaguiar.ecommerce.security;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.model.Perfil;
import br.com.jcaguiar.ecommerce.model.Usuario;
import br.com.jcaguiar.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

/**<h1>CONCEITO </h1>
 * Classe responsável pela lógica dos objetos Authentication.
 * A interface AuthenticationProvider serve justamente para o Spring reconhecer essa classe como tal. <br>
 *<h1>ATRIBUTOS </h1>
 * 	<b>userService:</b> classe DAO com os métodos CRUD da entidade Usuário.
 * @author JM Costal Aguiar
 */
@Component
public class ProvedorAutorizadorService implements AuthenticationProvider {

    @Autowired private UsuarioService userService;

    /**<hr><h2>PREPARANDO AUTENTICAÇÃO</h2>
     * Essa classe irá definir como o AuthenticationManager deverá agir. <br>
     * @param auth automaticamente concedido pelo Spring.
     * @return objeto Authentication com todos os atributos do Usuário compilados.
     * @throws AuthenticationException caso a validação dos atributos esteja errada.
     */
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
    	Console.log("<PROVEDOR AUTENTICAÇÃO>", +1);
    	final String email = auth.getName();
        final String senha = auth.getCredentials().toString();
        final List<Perfil> perfis = (List<Perfil>) auth.getAuthorities();
        Console.log("Perfils do Usuário: ");
        perfis.forEach(p -> Console.log(p.getAuthority()));
        //validando senha
        final BCryptPasswordEncoder encript = new BCryptPasswordEncoder();
        final String senhaCypt = userService.findByEmail(email).getSenha();
        final boolean validarSenha = encript.matches(senha, senhaCypt);
        try { assertTrue(validarSenha,""); }
        catch (Exception e) {
            throw new AuthenticationServiceException("");
        }
        //Retornando ao fluxo de autenticação
        Console.log("</PROVEDOR AUTENTICAÇÃO>", -1);
        return new UsernamePasswordAuthenticationToken(email, senha, perfis);
        //TODO: tratamento correto das possíveis excepções!
        // throw new BadCredentialsException("Este usuário está desativado.");
        // throw new UsernameNotFoundException("Login e/ou Senha inválidos.");
    }

    /**<hr><h2>CLASSES SUPORTADAS</h2>
     * TODO: aprender o que faz isso e completar o javadoc
     * @param auth
     * @return
     */
    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

    /**<hr><h2>VALIDAR STATUS</h2>
     * Valida se o usuário é ativo ou inativo.
     * @param usuario da classe Usuario.
     * @return true se status ativo ou false se inativo.
     */
    private boolean usuarioAtivo(Usuario usuario) {
        if (usuario.isEnabled()) {
            return true;
        }
        return false;
    }
}
