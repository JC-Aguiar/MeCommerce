package br.com.jcaguiar.ecommerce.util;

import br.com.jcaguiar.ecommerce.Console;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TratarString {
	
	public static char sigla(String text) {
		return text.charAt(0);
	}
	
	public static String getDepois(String text, String targetCharSequence) {
		text = text.toLowerCase();
		int index = text.indexOf(targetCharSequence);
		if(index == -1) {
			return text;
		}
		return text.substring(index+1).trim();
	}
	
	public static String getDepois(List<String> text, String targetCharSequence) {
		String textoFinal = "";
		for(String txt : text) {
			textoFinal += getDepois(txt, targetCharSequence) + " ";
		}
		return textoFinal;
	}

	public static String stackTraceToString(@NotNull StackTraceElement[] stack) {
		Console.log("<STACK-TRACE-TO-STRING>", +1);
		String finalStack = "";
		Console.log("STACK SIZE: " + stack.length);
		//Tratando cada Element da StrackTraceElement
		for(StackTraceElement element : stack) {
			//Convertendo Element para String
			final String frase = element.toString();
			Console.log("FRASE: " + frase);
			//Dividindo a frase em palavras, tendo como divisória o sinal "."
			String[] palavras = frase.split("\\.");
			Console.log("QUANT. PALAVRAS: " + palavras.length);
			//Recortando apenas o que importa: 3 últimas palavras da frase
			String text = Stream.of(palavras)
					.skip(palavras.length - 2)
					.collect(Collectors.joining("."));
			Console.log("PRINCIPAL: " + text);
			finalStack += text + "\n";
		}
		Console.log("STACK FINAL: ");
		Stream.of(
				finalStack.split("\n"))
				.forEach(Console::log);
		Console.log("</STACK-TRACE-TO-STRING>", -1);
		return finalStack;
	}

	public static String getMainException(@NotBlank String text) {
		return getEntre(text, "(", ")");
	}

	//TODO: Aperfeiçoar com Java 8
	public static String getEntre(@NotBlank String text, @NotBlank String charSequenceStart, @NotBlank String charSequenceEnd) {
		final int start = text.indexOf(charSequenceStart);
		final int end = text.indexOf(charSequenceEnd);
		return text.substring(start, end);
	}
	
	public static int paraInteiro(String text) throws NumberFormatException {
		text = text.replace(',', '.');
		int valor = Integer.parseInt(text);
		if(valor < 0) {
			throw new NumberFormatException("Valor negativo.");
		}
		return valor;
	}
	
	public static double paraDouble(String text) throws NumberFormatException {
		text = text.replace(',', '.');
		double valor = Double.parseDouble(text);
		if(valor < 0) {
			throw new NumberFormatException("Valor negativo.");
		}
		return valor;
	}
	
	public static BigDecimal paraBigDecimal(String text) throws NumberFormatException {
		text = text.replace(',', '.');
		double valor = Double.parseDouble(text);
		if(valor < 0) {
			throw new NumberFormatException("Valor negativo.");
		}
		return BigDecimal.valueOf(valor);
	}
	
	
	public static void main(String args[]) {
		String teste = "Marca: Teste2";
		final List<String> marcasArray= Arrays.asList(TratarString
				.getDepois(teste,":")
				.split(",")
		);
		System.out.println( marcasArray.toString() );
	}

}
