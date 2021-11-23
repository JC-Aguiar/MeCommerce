package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.dto.ProdutoImagemPOST;
import br.com.jcaguiar.ecommerce.model.ProdutoImagem;
import br.com.jcaguiar.ecommerce.projection.ProdutoImagemGET;
import br.com.jcaguiar.ecommerce.service.ImagemProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("**/imagem_produto")
public class ImagemProdutoController extends MasterController<ProdutoImagem, Long, ProdutoImagemPOST, ProdutoImagemGET> {

	public ImagemProdutoController(ImagemProdutoService imgService) {
		super(imgService, ProdutoImagem.class, ProdutoImagemPOST.class, ProdutoImagemGET.class);
	}
	
	@Override
	public ResponseEntity<List<?>> buscarTodos(HttpServletRequest request) {
		return new ResponseEntity<List<?>>(
			((ImagemProdutoService) masterService).findAll(),
			HttpStatus.OK			
		);
	}

	@Override
	public ResponseEntity<?> atualizar(@Valid ProdutoImagem objeto,
		HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> atualizarTodos(@Valid List<ProdutoImagem> objeto,
		HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletar(@Valid ProdutoImagem objeto,
		HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletarTodos(@Valid List<ProdutoImagem> objeto,
		HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
