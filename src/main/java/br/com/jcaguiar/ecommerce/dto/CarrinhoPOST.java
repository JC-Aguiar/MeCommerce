package br.com.jcaguiar.ecommerce.dto;

import br.com.jcaguiar.ecommerce.model.CarrinhoItem;

import java.math.BigDecimal;
import java.util.List;

final public class CarrinhoPOST implements MasterPOST {
	
	List<CarrinhoItem> item;
	BigDecimal total;

}
