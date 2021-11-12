package br.com.jcaguiar.ecommerce.handler;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.dto.ErroCadastroPOST;
import br.com.jcaguiar.ecommerce.exception.CadastroDuplicadoException;
import br.com.jcaguiar.ecommerce.exception.ErroInesperado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public final class ControllerHandler {
	
	@Autowired
	private MessageSource messageSource;

	private final String alerta = "ERRO INESPERADO.\n";

	private final String complemento =
			"Entre em contato com a equipe técnica ou responsável.\n" +
			"Recomendamos anotar o horário e a tela ou ação que estava tentando realizar.\n";

	//TODO: como criar novos Hendlers
	//TODO: na classe de tratamento geral realizar persistência dos erros/exceções no banco

	/**ERRO NO CADASTRO
	 * Tratamento de exceções quando o dto enviado ao sistema não atende aos requisitos dos atributos da classe modelo
	 * @param exc = MethodArgumentNotValidException
	 * @return ExceptionDto + HttpStatus 400 (BAD_REQUEST)
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroCadastroPOST> handler(MethodArgumentNotValidException exc) {
		//Coletando atributos inválidos
		List<FieldError> listaErro = exc.getBindingResult().getFieldErrors();
		//Preparando lista DTO
		List<ErroCadastroPOST> listaErroDto = new ArrayList<ErroCadastroPOST>();
		//Iterando listas
		for(FieldError erro : listaErro) {
			final String CAMPO =  erro.getField();
			final String MENSAGEM = messageSource.getMessage(erro, LocaleContextHolder.getLocale() );
			listaErroDto.add( new ErroCadastroPOST(CAMPO, MENSAGEM) );
		}
		//Retornando exceptionDto
		return listaErroDto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CadastroDuplicadoException.class)
	public ErroCadastroPOST handler(CadastroDuplicadoException exc) {
		return new ErroCadastroPOST(
				"email",
				exc.getMessage()
		);
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ErroInesperado.class)
	public String handler(ErroInesperado exc) {
		//Iniciando variáveis
		String mensagem = "";
		String tipoExc = exc.getClass().toString();
		Console.log( tipoExc.toUpperCase(Locale.ROOT) );
		//Tratando mensagem dependendo do erro
		switch (tipoExc) {
			case "ConfigurationException":
				mensagem = "A configuração entre cliente e servidor não está correta.";
				break;
			case "IllegalArgumentException":
				mensagem = "Alguns dos dados enviados estão em desacordo com os requisitos internos.";
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
				mensagem = exc.getMessage() + "\n";
		}
		//Retornando mensagem de erro
		return this.alerta + mensagem + this.complemento;
	}

//	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(PersistenceException.class)
//	public String handler(PersistenceException exc) {
//		return this.alerta +
//				"Ocorreu um erro interno ao tentar salvar os dados no banco de dados." +
//				this.complemento;
//	}

//	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(IllegalArgumentException.class)
//	public String handler(IllegalArgumentException exc) {
//		return this.alerta +
//				"Alguns dos dados enviados estão em desacordo com os requisitos internos." +
//				this.complemento;
//	}

//	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(ConfigurationException.class)
//	public String handler(ConfigurationException exc) {
//		return this.alerta +
//				"A configuração entre cliente e servidor não está correta." +
//				this.complemento;
//	}

//	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(MappingException.class)
//	public String handler(MappingException exc) {
//		return this.alerta +
//				"O servidor não conseguiu relacionar os dados entre si." +
//				this.complemento;
//	}

//	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(NullPointerException.class)
//	public String handler(NullPointerException exc) {
//		return this.alerta +
//				"O servidor não conseguiu relacionar os dados entre si." +
//				this.complemento;
//	}

//	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(CadastroDuplicadoException.class)
//	public ErroCadastroPOST handler(CadastroDuplicadoException exc) {
//		//Coletando atributos inválidos
//		MessageSourceResolvable erro = new FieldError(
//				exc.getObjetoNome(),
//				exc.getCampo(),
//				exc.getMessage());
//		//Preparando lista DTO
//		final String CAMPO =  exc.getCause().getMessage();
//		final String MENSAGEM = messageSource.getMessage(erro, LocaleContextHolder.getLocale() );
//		return new ErroCadastroPOST(CAMPO, MENSAGEM);
//	}
	
}