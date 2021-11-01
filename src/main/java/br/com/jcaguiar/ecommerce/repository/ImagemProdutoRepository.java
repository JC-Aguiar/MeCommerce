package br.com.jcaguiar.ecommerce.repository;

import org.springframework.stereotype.Repository;

import br.com.jcaguiar.ecommerce.model.ProdutoImagem;

@Repository
public interface ImagemProdutoRepository extends MasterRepository<ProdutoImagem, Long> {
	
//	@Query("SELECT ip.imagem FROM imagem_produto ip WHERE ip.id = :produto")
//	List<ProdutoImagem> findByProduto(Produto produto);
}
