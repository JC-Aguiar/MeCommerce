package br.com.jcaguiar.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.jcaguiar.ecommerce.model.NotaFiscal;
import br.com.jcaguiar.ecommerce.repository.NotaFiscalRepository;

@Service
public class NotaFiscalService extends MasterService<NotaFiscal, Long> {
	
	public NotaFiscalService(NotaFiscalRepository nfRepo) {
		super(nfRepo);
	}

	@Override
	public Optional<NotaFiscal> findByIdUser(Long id) {
		return null;
	}

	@Override
	public List<NotaFiscal> findAllBasic() {
		return null;
	}

	@Override
	public List<NotaFiscal> findByNomeContaining(String nome) {
		return null;
	}

	@Override
	public List<NotaFiscal> findByNomeContainingBasic(String nome) {
		return null;
	}

	@Override
	public List<NotaFiscal> findByNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

}
