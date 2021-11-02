package br.com.jcaguiar.ecommerce.model;

import lombok.*;

import javax.persistence.*;
/**
 * TODO: terminar javadoc
 *
 * @author JM Costal Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "setor")
final public class Setor implements Entidade<Short> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;

	@Column(nullable = false, unique = true)
	private String nome;

}
