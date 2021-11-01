package br.com.jcaguiar.ecommerce.handler;

import br.com.jcaguiar.ecommerce.dto.ErroCadastroPOST;
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

@RestControllerAdvice
public final class ErroCadastroHandler {
	
	@Autowired
	private MessageSource messageSource;

	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
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
	@ExceptionHandler(org.postgresql.util.PSQLException.class)
	public String handler(org.postgresql.util.PSQLException exc) {
		return "ERRO: E-mail duplicado";
	}
	
}
