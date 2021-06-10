package br.com.jcaguiar.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.jcaguiar.ecommerce.model.Acesso;
import br.com.jcaguiar.ecommerce.repository.PedidoRepository;

@Service
public class PedidoService extends MasterService<Acesso, Long> {
	
	public PedidoService(PedidoRepository pedidoRepo) {
		super(pedidoRepo);
	}

	@Override
	public Optional<Acesso> findByIdLimited(Long id) {
		return null;
	}

	@Override
	public List<Acesso> findAllLimited() {
		return null;
	}

	@Override
	public List<Acesso> findByNomeContaining(String nome) {
		return null;
	}

	@Override
	public List<Acesso> findByNomeContainingLimited(String nome) {
		return null;
	}

}
