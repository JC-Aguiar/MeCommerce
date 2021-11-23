package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.dto.FornecedorPOST;
import br.com.jcaguiar.ecommerce.model.Fornecedor;
import br.com.jcaguiar.ecommerce.projection.FornecedorGET;
import br.com.jcaguiar.ecommerce.service.FornecedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController extends MasterController<Fornecedor, Short, FornecedorPOST, FornecedorGET> {

	public FornecedorController(FornecedorService fornecedorService) {
		super(fornecedorService, Fornecedor.class, FornecedorPOST.class, FornecedorGET.class);
	}

	@Override
	public ResponseEntity<?> atualizar(@Valid Fornecedor objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> atualizarTodos(@Valid List<Fornecedor> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletar(@Valid Fornecedor objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletarTodos(@Valid List<Fornecedor> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
