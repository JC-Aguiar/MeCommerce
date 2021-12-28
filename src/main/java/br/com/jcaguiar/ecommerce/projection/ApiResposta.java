package br.com.jcaguiar.ecommerce.projection;

import br.com.jcaguiar.ecommerce.util.ApiProcesso;
import br.com.jcaguiar.ecommerce.util.ApiRespostaEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResposta {

    Page<?> pagina;
    final int registros;
    final ApiRespostaEnum resultado;
    final List<String> mensagens = new ArrayList<>();

    /**<hr><h2>CONSTRUTOR 1: BÁSICO</h2>
     * TODO: completar javadoc
     * @param processos
     */
    public ApiResposta(List<ApiProcesso<?>> processos) {
        this.registros = processos.size();
        this.resultado = statusProcessos(processos);
    }


    /**<hr><h2>CONSTRUTOR 2: +PAGE</h2>
     * TODO: completar javadoc
     * @param processos
     * @param pagina
     */
    public ApiResposta(List<ApiProcesso<?>> processos, Page pagina) {
        this.pagina = pagina;
        this.registros = processos.size();
        this.resultado = statusProcessos(processos);
    }

    /**<hr><h2>IDENTIFICA RESULTADO DOS PROCESSOS</h2>
     * TODO: finalizar javadoc
     * @param processos
     * @return
     */
    private ApiRespostaEnum statusProcessos(List<ApiProcesso<?>> processos) {
        final boolean erroParcial = processos.stream().anyMatch(ApiProcesso::isErro);
        final boolean erroTotal = processos.stream().allMatch(ApiProcesso::isErro);
        if (erroParcial) {
            if (erroTotal) {
                return ApiRespostaEnum.ERRO_PARCIAL;
            } else {
                return ApiRespostaEnum.ERRO_PARCIAL;
            }
        }
        return ApiRespostaEnum.SUCESSO;
    }

    /**<hr><h2>ADICIONAR MENSAGEM POR TEXTO</h2>
     * TODO: completar javadoc
     * @param texto
     * @return
     */
    public List<String> addMensagem(String texto) {
        this.mensagens.add(texto);
        return this.mensagens;
    }

    /**<hr><h2>ADICIONAR MENSAGEM POR API-PROCESSO</h2>
     * TODO: completar javadoc
     * @param processo
     * @return
     */
    public List<String> addMensagem(ApiProcesso<?> processo) {
        this.mensagens.add(processo.getCausa());
        return this.mensagens;
    }

    /**<hr><h2>ADICIONAR MENSAGENS POR LISTA DE API-PROCESSO</h2>
     * TODO: completar javadoc
     * @param processos
     * @return
     */
    public List<String> addMensagem(List<ApiProcesso<?>> processos) {
        processos.forEach(this::addMensagem);
        return this.mensagens;
    }

    /**<hr><h2>DEFINIR MENSAGEM POR API-PROCESSO</h2>
     * TODO: completar javadoc
     * @param processo
     * @return
     */
    public List<String> setMensagem(ApiProcesso<?> processo) {
        this.mensagens.clear();
        addMensagem(processo);
        return this.mensagens;
    }

    /**<hr><h2>DEFINIR MENSAGEM POR LISTA DE API-PROCESSO</h2>
     * TODO: completar javadoc
     * @param processos
     * @return
     */
    public List<String> setMensagem(List<ApiProcesso<?>> processos) {
        this.mensagens.clear();
        processos.forEach(this::addMensagem);
        return this.mensagens;
    }

}
