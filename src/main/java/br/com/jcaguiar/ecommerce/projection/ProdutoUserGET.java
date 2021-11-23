package br.com.jcaguiar.ecommerce.projection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor //@AllArgsConstructor
final public class ProdutoUserGET implements MasterGET {

	CategoriaGET categoria;
	List<MarcaGET> marca = new ArrayList<>();
	String nome;
	String descricao;
	String modelo;
	BigDecimal valor;
	String tamanho;
	String medidas;
	String material;
	List<ProdutoImagemGET> imagem = new ArrayList<>();
	List<ComentarioGET> comentario = new ArrayList<>();
	int votos;
	short nota;

}
