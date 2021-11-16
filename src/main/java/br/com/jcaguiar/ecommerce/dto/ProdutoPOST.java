package br.com.jcaguiar.ecommerce.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE)
final public class ProdutoPOST implements MasterPOST {
	
	String categoriaNome;
	List<String> marcaNome;
	@Size(min = 3) String nome;
	@Size(min = 3) String descricao;
	@Min(value = 1) BigDecimal valor;
	@Size(min = 1, max = 1) String tamanho;
	String material;
	List<String> imagem;
	
	@Override 
	public String toString() {
		String stringMarcas = (marcaNome == null) ? null : marcaNome.toArray().toString();
		String stringImagens = (imagem == null) ? null : imagem.toArray().toString();
		
		return String.format(
			"Nome: %s\n"
			+ "Descrição: %s\n"
			+ "Marcas: %s\n"
			+ "Categoria: %s\n"
			+ "Valor: %d\n"
			+ "Tamanho: %s\n"
			+ "Materiais: %s\n"
			+ "Imagens: %s\n", 
			nome, 
			descricao,
			stringMarcas,
			categoriaNome,
			valor,
			tamanho,
			material,
			stringImagens
		);
	}
	
//	public ProdutoPOST(Produto produto) {
//		System.out.printf("CRIANDO DTO\n");
//		this.id = produto.getId();
//		this.categoria = produto.getCategoria().getNome();
//		this.nome = produto.getNome();
//		this.descricao = produto.getDescricao();
//		this.modelo = produto.getModelo();
//		this.valor = produto.getValor();
//		produto.getMarca().forEach(m->{
//			this.marca.add( m.getNome() );
//		});
//		produto.getFornece().forEach(f->{
//			this.fornacedor.add( f.getFornecedor().getNome() );
//		});
//		this.tamanho = produto.getTamanho();
//		this.medidas = produto.getMedidas();
//		this.estoque = produto.getEstoque();
//		this.acessos = produto.getAcessos();
//		this.votos = produto.getVotos();
//		this.nota = produto.getNota();
//		produto.getImagem().forEach(img->{
//			this.imagem.add( img.getImagem() );
//		});
//	}
	
}
