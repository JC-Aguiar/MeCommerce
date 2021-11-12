package br.com.jcaguiar.ecommerce.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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

	public static String stackTraceToString(StackTraceElement[] stack) {
		String text = "";
		for(StackTraceElement element : stack) {
			final String elementText = element.toString();
			String[] elementArray = elementText.split(".");
			final int elementSize = elementArray.length;
			text += Arrays.stream(elementArray)
					.skip(elementSize - 3)
					.toString()
					+ "\n";
		}
		return text;

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
