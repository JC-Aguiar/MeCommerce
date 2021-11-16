package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.dto.NotaFiscalPOST;
import br.com.jcaguiar.ecommerce.model.NotaFiscal;
import br.com.jcaguiar.ecommerce.projection.NotaFiscalGET;
import br.com.jcaguiar.ecommerce.service.NotaFiscalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/nf")
public class NotaFiscalController extends MasterController<NotaFiscal, Long, NotaFiscalPOST, NotaFiscalGET> {

	public NotaFiscalController(NotaFiscalService nfService) {
		super(nfService);
	}

	@Override
	public ResponseEntity<?> atualizar(@Valid NotaFiscal objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> atualizarTodos(@Valid List<NotaFiscal> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletar(@Valid NotaFiscal objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletarTodos(@Valid List<NotaFiscal> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
