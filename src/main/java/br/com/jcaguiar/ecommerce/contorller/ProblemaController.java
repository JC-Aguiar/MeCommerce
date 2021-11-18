package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.exception.ErroInesperadoException;
import br.com.jcaguiar.ecommerce.model.Problema;
import br.com.jcaguiar.ecommerce.service.ProblemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/erro")
public class ProblemaController extends MasterController<Problema, Long, ErroInesperadoException, ErroInesperadoException> {

	@Autowired
	private ProblemaService ERRO_SERVICE;

	public ProblemaController(ProblemaService erroService) {
		super(erroService);
	}

	@Override
	@GetMapping
	@Transactional
	public ResponseEntity<?> buscarTodos(HttpServletRequest request) {
		final Sort ordene = Sort.by("id").ascending();
		log(0);
		Console.log("Coletando e retornando dados");
		return paginanar(masterService.findAll(), ordene, 0);
	}


	@Override
	public ResponseEntity<?> atualizarTodos(List objeto, HttpServletRequest request) {
		return null;
	}


	@Override
	public ResponseEntity<?> deletarTodos(List objeto, HttpServletRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<?> atualizar(Problema objeto, HttpServletRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<?> deletar(Problema objeto, HttpServletRequest request) {
		return null;
	}
}
