package br.com.jcaguiar.ecommerce.filter;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.model.Acesso;
import br.com.jcaguiar.ecommerce.model.Produto;
import br.com.jcaguiar.ecommerce.model.Usuario;
import br.com.jcaguiar.ecommerce.service.ProdutoService;
import br.com.jcaguiar.ecommerce.service.UsuarioService;
import br.com.jcaguiar.ecommerce.util.DataFormato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class AcessoFilter implements HandlerInterceptor {

	@Autowired UsuarioService userService;
	@Autowired ProdutoService prodService;
	private Usuario usuario;
	private Acesso acesso;

	@Override
	final public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	throws Exception {
		usuario = getUsuarioLogin(request);
		acesso = Acesso.builder()
				.usuario(usuario)
				.url(request.getRequestURI())
				.build();
//		request.setAttribute("acesso", acesso);
		return true;
	}

	@Override
	final public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	throws Exception {
		acesso.setDuracao(
				Duration.between(acesso.getData_acesso(), LocalDateTime.now())
		);
		Console.log(String.format(
				"<FILTRO DE ACESSOS> \n"
				+ "\tACESSO:   %s\n"
				+ "\tUSER:     %s\n"
				+ "\tURL:      %s\n"
				+ "\tDURAÇÃO:  %d.%ds\n"
				+ "</FILTRO DE ACESSOS> \n",
				DataFormato.formatar(acesso.getData_acesso()),
				usuario.getEmail(),
				acesso.getUrl(),
				acesso.getDuracao().getSeconds(),
				acesso.getDuracao().getNano())
		);
		//HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	//USER CHECK
	final private Usuario getUsuarioLogin(HttpServletRequest request) {
		try {
			final String USER_NAME = request.getUserPrincipal().getName();
			return (Usuario) userService.findByNomeContaining(USER_NAME).get(0);
		}
		catch (Exception e) {
			return Usuario.builder()
					.email("{Usuario-não-logado}")
					.build();
		}
	}
	
	//PRODUTO CHECK
	//UNFINISHED!!!!!!!!
	final private Produto getProdutoAcesso(String url) {
		final String[] urlArray = url.substring(1).split("/");
		if(!urlArray[0].startsWith("produto")) {
			//cancele se url não aponta para /produto(s)
			return Produto.builder().nome(("{Produto-não-acessado}")).build();
		}
		try {
			//se converter o número após "produto/", retorne o produto desse id
			final int prodId = Integer.parseInt(urlArray[1]);
			return prodService.findById(prodId);
		}catch (Exception e) {
			//cancele caso não identifique o produto ou o id do produto
			return Produto.builder().nome(("{Produto-não-acessado}")).build();
		}
	}
}
