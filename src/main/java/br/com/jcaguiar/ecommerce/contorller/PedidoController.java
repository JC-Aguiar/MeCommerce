package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.dto.PedidoPOST;
import br.com.jcaguiar.ecommerce.model.Pedido;
import br.com.jcaguiar.ecommerce.projection.PedidoGET;
import br.com.jcaguiar.ecommerce.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController extends MasterController<Pedido, Long, PedidoPOST, PedidoGET> {

	public PedidoController(PedidoService pedidoService) {
		super(pedidoService, Pedido.class, PedidoPOST.class, PedidoGET.class);
	}

	@Override
	public ResponseEntity<?> atualizar(@Valid Pedido objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> atualizarTodos(@Valid List<Pedido> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletar(@Valid Pedido objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletarTodos(@Valid List<Pedido> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
