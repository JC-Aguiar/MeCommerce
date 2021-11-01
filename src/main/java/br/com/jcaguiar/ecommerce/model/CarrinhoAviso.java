package br.com.jcaguiar.ecommerce.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

/**<h1>CONCEITO</h1>
 * Mensagens relacionadas com os Produtos que ainda constam no Carrinho do Usuários <br>
 * Alertas na alteração de algum produto (preço, estoque, etc) ou avisos promocionais.
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> identificador Long do registro no banco de dados. <br>
 * <b>aviso:</b> texto String das mensagems.<br>
 * <b>data_visto:</b> data-hora LocalDateTime em que o aviso foi visto pelo usuário <br>
 * @author João MC Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "carrinho_aviso")
public class CarrinhoAviso extends EntidadeData<Long> {

    /**
     * TODO: <br>
     *  consultar como alterar tabelas/colunas no heroku, uma vez que já criadas. <br>
     *  considerar alterar atributo "aviso" para "mensagem". <br>
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String aviso;

    LocalDateTime data_visto;

}
