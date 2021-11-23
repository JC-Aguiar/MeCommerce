package br.com.jcaguiar.ecommerce.exception;

import br.com.jcaguiar.ecommerce.projection.MasterGET;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ErroCadastro implements MasterGET {

	Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() );
	int status;
	String error;
	Map<String, String> mensagem = new HashMap<>();
	String path;

	public ErroCadastro(HttpStatus http, String path) {
		this.status = http.value();
		this.error = http.getReasonPhrase();
		this.path = path;
	}

	public void addMensagem(String campo, String erro) {
		this.mensagem.put(campo, erro);
	}

}
