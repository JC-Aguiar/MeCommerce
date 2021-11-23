package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.dto.CategoriaPOST;
import br.com.jcaguiar.ecommerce.model.Categoria;
import br.com.jcaguiar.ecommerce.projection.CategoriaGET;
import br.com.jcaguiar.ecommerce.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController extends MasterController<Categoria, Short, CategoriaPOST, CategoriaGET> {

	public CategoriaController(CategoriaService categoriaService) {
		super(categoriaService, Categoria.class, CategoriaPOST.class, CategoriaGET.class);
	}

	@Override
	public ResponseEntity<?> atualizar(@Valid Categoria objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> atualizarTodos(@Valid List<Categoria> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletar(@Valid Categoria objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletarTodos(@Valid List<Categoria> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
