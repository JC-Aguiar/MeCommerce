package br.com.jcaguiar.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableCaching
@SpringBootApplication
public class EcommerceApplication {
	
	/**SISTEMA E-COMMERCE
	 * 
	 * API:
	 * 	> Rest
	 * 	> Desacoplamento
	 * 	> Escalabilidade
	 * 	> Manutenção
	 * 	> Verbosidade
	 * 
	 * TECNOLOGIAS:
	 *  > Spring Boot
	 *  > Spring Data
	 *  > Spring MVC
	 *  > Spring Security
	 *  > Spring Validation
	 *  > Spring Web
	 *  > Spring Test
	 *  > Spring WebFlux
	 *  > MySQL
	 *  > Lombok
	 *  > OpenCsv
	 *  > ModelMapper
	 *  > JWT
	 * 
	 * IDE:
	 * 	> Eclipse
	 * 	> Eclipse Color Theme
	 * 	> EditBox
	 * 
	 * USUÁRIOS FINAIS:
	 * 	> Usuário
	 * 	> Proprietário
	 * 	> Administrador
	 * 
	 * <Proprietario>
	 * 		Cadastrar, Desativar/Ativar, Alterar e Remover:
	 * 			> Setores
	 * 			> Categorias
	 * 			> Produtos
	 * 			> Fornecedores
	 * 			> Transportadoras
	 * 			> Marcas
	 * 			> Meios de Pagamento
	 * 			> Lançamento
	 * 		Inserir imagens para os Produtos
	 * 		Cadastrar endereços e localidades
	 * 		Consultar os Pedidos dos Clientes
	 * 		Consultar quantidade de Acessos aos Produtos
	 * 		Consultar Comentários feitos aos Produtos
	 * 		Consultar Notas Fiscais emitidas
	 * 		Consultar Produtos que constam no Carrinho dos Usuários/Clientes
	 * 		Consultar localidades e regiões dos Usuários/Clientes
	 * 		Consultar taxa dos Meios de Pagamento utilizados nas compras 
	 * 
	 * <Administrador>
	 * 		(Proprietário com mais permissóes)
	 * 		Monitorar Status da API
	 * 		Consultar LOG do Servidor
	 * 		Consultar dados dos Usuários/Clientes
	 * 
	 * <Usuario>
	 * 		Se cadastrar
	 * 		Realizar login
	 * 		Configurar Conta do Usuário
	 * 		Cadastrar Endereços
	 * 		Recuperar Senha
	 * 		Consultar Carrinho do Usuário
	 * 		Consultar Histórico de Pedidos
	 * 		Consultar Depoimentos do Usuário
	 * 		Pesquisar conteúdos do site
	 * 		Consultar catálogo de Produtos com filtros dinâmicos:
	 * 			> Categoria
	 * 			> Prodmoção
	 * 		Show-case de Produtos novos:(Lançamentos)
	 * 		Comprar Produtos
	 * 		Comentar Produtos
	 * 		Dar nota para Produtos
	 */

	/*
	TODO: ESTRUTURA DE REPOSTA JSON PARA AS REQUISIÇÕES
		{
			pagina: {
				logo: {
					url: String
				},
				menu: {
					carrinho: [
						carrinhoItem: {
							nome: String,
							valor: BigDecimal,
							marcas: String,
							quantidade: Short,
							imagem: String
						},
						totalItens: Integer
					],
					usuario: {
						sigla: String,
						email: String,
						avisos: [
							mensagem: String
						],
						perfil: String,
						logout: String
					},
					acesso: {
						login: String,
						novoCadastro: String,
						novaSenha: String
					}
				},
				links: [
					nome: String,
					url: String
				],
				conteudo: {
				}
			}
		}
	}*/

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
		
	}

}
