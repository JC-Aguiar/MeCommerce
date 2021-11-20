package br.com.jcaguiar.ecommerce.util;

import br.com.jcaguiar.ecommerce.Console;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;


@Converter
public class StringListJsonConverter implements AttributeConverter<List<String>, String> {

	private final static ObjectMapper mapper = new ObjectMapper();

	
	/**CONVERTER PARA BANDO DE DADOS
	 * 
	 */
	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		Console.log("[JSON-CONVERTER]: Convertendo Json para String");
		try {
//			Console.log( Arrays.toString( attribute.toArray() ), +1 );
//			Console.lvDown();
			return mapper.writeValueAsString(attribute);
		}
		catch (Exception ex) {
			Console.log("[JSON-CONVERTER]: Erro na conversão");
			return null;
		}
	}

	/**CONVERTER PARA CLASSE
	 *
	 */
	@Override
	public List<String> convertToEntityAttribute(String dbText) {
		Console.log("[JSON-CONVERTER]: Convertendo String para Json");
		try {
//			Console.log( dbText, +1 );
//			Console.lvDown();
			return mapper.readValue(dbText, ArrayList.class);
		}
		catch (Exception ex) {
			Console.log("[JSON-CONVERTER]: Erro na conversão");
			return null;
		}
	}

}
