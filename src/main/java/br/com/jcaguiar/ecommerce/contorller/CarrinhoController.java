package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.dto.CarrinhoPOST;
import br.com.jcaguiar.ecommerce.model.Carrinho;
import br.com.jcaguiar.ecommerce.projection.CarrinhoGET;
import br.com.jcaguiar.ecommerce.service.CarrinhoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController extends MasterController<Carrinho, Long, CarrinhoPOST, CarrinhoGET> {

	public CarrinhoController(CarrinhoService carrinhoService) {
		super(carrinhoService);
	}

	@Override
	public ResponseEntity<?> atualizar(@Valid Carrinho objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> atualizarTodos(@Valid List<Carrinho> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletar(@Valid Carrinho objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletarTodos(@Valid List<Carrinho> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}





}
