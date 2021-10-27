package br.com.jcaguiar.ecommerce.contorller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jcaguiar.ecommerce.model.Cidade;
import br.com.jcaguiar.ecommerce.service.CidadeService;

@RestController
@RequestMapping("**/cidade")
public class CidadeController {
	
	@Autowired
	private CidadeService CIDADE_SERVICE;
	
	//BUSCA GERAL
	@GetMapping
	public ResponseEntity<List<?>> findAll(HttpServletRequest request) {
		final Sort ORDENE = Sort.by("id").ascending();
		List<Cidade> cidades = CIDADE_SERVICE.findAll(ORDENE);
		return new ResponseEntity<>(cidades, HttpStatus.FOUND);
	}
	
	//BUSCA ESPECIFICA ID
	@GetMapping("/{var}")
	public ResponseEntity<?> findOneId(@PathVariable(name = "var")String var, HttpServletRequest request) {
		try {
			final int ID = Integer.parseInt(var);
			final Sort ORDENE = Sort.by("id").ascending();
			final Optional<Cidade> cidade = CIDADE_SERVICE.findById(ID);
			return new ResponseEntity<>(cidade, HttpStatus.FOUND);
		}
		catch (NumberFormatException e) {
			return findOneNome(var, request);
		}
	}
	
	//BUSCA ESPECIFICA NOME
	public ResponseEntity<List<?>> findOneNome(String nome, HttpServletRequest request) {
		final Sort ORDENE = Sort.by("id").ascending();
		final List<Cidade> cidades = CIDADE_SERVICE.findByNomeContaining(nome, ORDENE);
		return new ResponseEntity<>(cidades, HttpStatus.FOUND);
	}

	
}
