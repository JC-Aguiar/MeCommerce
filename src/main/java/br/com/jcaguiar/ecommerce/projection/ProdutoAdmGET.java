package br.com.jcaguiar.ecommerce.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
final public class ProdutoAdmGET implements MasterGET {

	private boolean ativo = true;
	private LocalDateTime data_cadastro;
	private CategoriaGET categoria;
	private List<MarcaGET> marca = new ArrayList<>();
	private String nome;
	private String descricao;
	private String modelo;
	private BigDecimal valor;
	private short estoque;
	private String tamanho;
	private String medidas;
	private String material;
	private String codigo;
	private List<ForneceGET> fornece = new ArrayList<>();
	private List<ProdutoImagemGET> imagem = new ArrayList<>();
	private List<ComentarioGET> comentario = new ArrayList<>();
	private int acessos;
	private int votos;
	private short nota;
	private LocalDateTime data_ativo;
	private LocalDateTime data_desativo;
	
}
