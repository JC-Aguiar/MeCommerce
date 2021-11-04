package br.com.jcaguiar.ecommerce.handler;

import br.com.jcaguiar.ecommerce.dto.ErroCadastroPOST;
import br.com.jcaguiar.ecommerce.exception.EmailDuplicadoException;
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

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public final class ErroCadastroHandler {
	
	@Autowired
	private MessageSource messageSource;

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
	@ExceptionHandler(EmailDuplicadoException.class)
	public ErroCadastroPOST handler(EmailDuplicadoException exc) {
		return new ErroCadastroPOST(
				"email",
				exc.getMessage()
		);
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ErroInesperado.class)
	public String handler(ErroInesperado exc) {
		return exc.getMessage();
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(PersistenceException.class)
	public String handler(PersistenceException exc) {
		return "Erro Inesperado: " + exc.getMessage();
	}

//	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(EmailDuplicadoException.class)
//	public ErroCadastroPOST handler(EmailDuplicadoException exc) {
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
