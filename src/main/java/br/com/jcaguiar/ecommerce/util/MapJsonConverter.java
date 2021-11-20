package br.com.jcaguiar.ecommerce.util;

import br.com.jcaguiar.ecommerce.Console;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


@Converter
public class MapJsonConverter<K, V> implements AttributeConverter<Map<K, V>, String> {

	private final static ObjectMapper mapper = new ObjectMapper();
	@Getter private K key;
	@Getter private V value;

	
	/**CONVERTER PARA BANDO DE DADOS
	 * 
	 */
	@Override
	public String convertToDatabaseColumn(Map<K, V> attribute) {
		Console.log("[JSON-CONVERTER]: Convertendo Json para String");
		try {
			Console.log( Arrays.toString( attribute.values().toArray() ), +1 );
			Console.lvDown();
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
	public Map<K, V> convertToEntityAttribute(String dbText) {
		Console.log("[JSON-CONVERTER]: Convertendo String para Json");
		try {
			Console.log( dbText, +1 );
			Console.lvDown();
			Map<K, V> attribute = mapper.readValue(dbText, Map.class);
			SortedMap<K, V> map = new TreeMap<>(attribute).descendingMap();
			return map;
		}
		catch (Exception ex) {
			Console.log("[JSON-CONVERTER]: Erro na conversão");
			return null;
		}
	}

}
