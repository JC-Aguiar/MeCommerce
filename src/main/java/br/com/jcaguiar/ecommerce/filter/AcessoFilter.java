package br.com.jcaguiar.ecommerce.filter;

import br.com.jcaguiar.ecommerce.model.Acesso;
import br.com.jcaguiar.ecommerce.model.Produto;
import br.com.jcaguiar.ecommerce.model.Usuario;
import br.com.jcaguiar.ecommerce.service.ProdutoService;
import br.com.jcaguiar.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class AcessoFilter implements HandlerInterceptor {

	@Autowired UsuarioService userService;
	@Autowired ProdutoService prodService;

	@Override
	final public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	throws Exception {
		//final Sort ordene = Sort.by("id").ascending();
		final Usuario USUARIO = getUsuarioLogin(request);
		final Acesso ACESSO = Acesso.builder()
				.usuario(USUARIO)
				.url(request.getRequestURI())
				.build();
		request.setAttribute("acesso", ACESSO);
		return true;
	}

	@Override
	final public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	throws Exception {
		Acesso acesso = (Acesso) request.getAttribute("acesso");
		acesso.setDuracao( Duration.between(acesso.getData_acesso(), LocalDateTime.now()) );
		System.out.printf( acesso.report() );
		//HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	//USER CHECK
	final private Usuario getUsuarioLogin(HttpServletRequest request) {
		try {
			final String USER_NAME = request.getUserPrincipal().getName();
			return (Usuario) userService.findByNomeContaining(USER_NAME).get(0);
		}
		catch (Exception e) {
			return Usuario.builder().email("{Usuario-não-logado}").build();
		}
	}
	
	//PRODUTO CHECK
	//UNFINISHED!!!!!!!!
	final private Produto getProdutoAcesso(String url) {
		final String[] URL = url.substring(1).split("/");
		System.out.println("teste " + Arrays.toString(URL) );
		if(!URL[0].startsWith("produto")) {
			//cancele se url não aponta para /produto(s)
			return Produto.builder().nome(("{Produto-não-acessado}")).build();
		}
		try {
			//se converter o número após "produto/", retorne o produto desse id
			final int PROD_ID = Integer.parseInt(URL[1]);
			return prodService.findById(PROD_ID).get();
		}catch (Exception e) {
			//cancele caso não identifique o produto ou o id do produto
			return Produto.builder().nome(("{Produto-não-acessado}")).build();
		}
	}
}
