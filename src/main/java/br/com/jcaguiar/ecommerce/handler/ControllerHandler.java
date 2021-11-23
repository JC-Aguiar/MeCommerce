package br.com.jcaguiar.ecommerce.handler;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.exception.CadastroDuplicadoException;
import br.com.jcaguiar.ecommerce.exception.ErroCadastro;
import br.com.jcaguiar.ecommerce.exception.ErroInesperado;
import br.com.jcaguiar.ecommerce.model.Problema;
import br.com.jcaguiar.ecommerce.service.ProblemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
public final class ControllerHandler {
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProblemaService problemaService;

	//TODO: como criar novos Handlers
	//TODO: na classe de tratamento geral realizar persistência dos erros/exceções no banco

	/**ERRO NO CADASTRO
	 * Tratamento de exceções quando o dto enviado ao sistema não atende aos requisitos dos atributos da classe modelo
	 * @param exc = MethodArgumentNotValidException
	 * @return ExceptionDto + HttpStatus 400 (BAD_REQUEST)
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErroCadastro handler(MethodArgumentNotValidException exc, HttpServletRequest request) {
		//Coletando atributos inválidos
		List<FieldError> atributosErro = exc.getBindingResult().getFieldErrors();
		//Instanciando e preenchendo DTO com a lista dos campos com erro
		ErroCadastro erroDto = new ErroCadastro(HttpStatus.BAD_REQUEST, request.getRequestURI());
		for(FieldError atributo : atributosErro) {
			final String campo =  atributo.getField();
			final String erro = messageSource.getMessage(atributo, LocaleContextHolder.getLocale() );
			erroDto.addMensagem(campo, erro);
		}
		return erroDto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CadastroDuplicadoException.class)
	public ErroCadastro handler(CadastroDuplicadoException exc, HttpServletRequest request) {
		ErroCadastro erroDto = new ErroCadastro(HttpStatus.BAD_REQUEST, request.getRequestURI());
		erroDto.addMensagem("email", exc.getMessage());
		return erroDto;
	}

	//@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handler(RuntimeException exc, HttpServletRequest request) {
		Console.log("<CONTROLLER-HANDLER>", +1);
		String mensagem = exc.getMessage();
		String tipoExc = exc.getClass().getSimpleName();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		//exc.getSuppressed();
		//Tratando mensagem dependendo do erro
		switch (tipoExc) {
			case "ConfigurationException":
				mensagem = "A configuração entre cliente e servidor não está correta.";
				break;
			case "IllegalArgumentException":
				mensagem = "Alguns dos dados enviados/solicitados estão em desacordo com os requisitos internos.";
				status = HttpStatus.BAD_REQUEST;
				break;
			case "ServerSideException":
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			case "ClientSideException":
				status = HttpStatus.BAD_REQUEST;
				break;
			case "MappingException":
				mensagem = "O servidor não conseguiu relacionar os dados entre si.";
				break;
			case "PersistenceException":
				mensagem = "Ocorreu um erro interno ao tentar salvar os dados no banco de dados.";
				break;
			case "NullPointerException":
				mensagem = "O servidor não conseguiu processar os dados enviados.";
				break;
			default:
				mensagem = mensagem.length() < 2 ? "Inconformidade em manutenção (#EXCEP-11)." : mensagem;
		}
		//Persistindo stackTrace do erro no banco
		Console.log("Salvando exception no banco de dados.");
		Problema problema = problemaService.salvar( new Problema(exc) );
		//Exibindo stack resumida dos erros
		Console.log("<STACK-TRACE>", +1);
		problema.getStack().forEach(Console::log);
		Console.log("</STACK-TRACE>", -1);
		//Retornando mensagem de erro
		Console.log("Retornando mensagem ao cliente.");
		Console.log("</CONTROLLER-HANDLER>", -1);
		return new ResponseEntity<>(
				new ErroInesperado(status, mensagem, request.getRequestURI()),
				status);
	}
	
}
