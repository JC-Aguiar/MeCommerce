package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.dto.TransportadorPOST;
import br.com.jcaguiar.ecommerce.model.Transportador;
import br.com.jcaguiar.ecommerce.projection.TransportadorGET;
import br.com.jcaguiar.ecommerce.service.TransportadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transp")
public class TransportadorController extends MasterController<Transportador, Short, TransportadorPOST, TransportadorGET> {

	public TransportadorController(TransportadorService transpService) {
		super(transpService, Transportador.class, TransportadorPOST.class, TransportadorGET.class);
	}

	@Override
	public ResponseEntity<?> atualizar(@Valid Transportador objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> atualizarTodos(@Valid List<Transportador> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletar(@Valid Transportador objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletarTodos(@Valid List<Transportador> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
