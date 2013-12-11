package com.pe.pgn.clubpgn.util;

import org.apache.commons.lang.StringUtils;

import com.pe.pgn.clubpgn.common.CLPConstantes;

public class StringUtil {

	/**
	 * 
	 * @param numero cadena a la que se va aumentar caracteres a la izquierda
	 * @param longitud longitud que debe tener la cadena resultante 
	 * @param cadenaRelleno caracter que se va aumentar a la izquierda de numero hasta alcanzar la longitud señalada
	 * @param prefijo un caracter que al final se va agregar como prefijo a numero
	 * @return la cadena resultante 
	 */
	public static String getStringLeftPad(String numero, int longitud, String cadenaRelleno, String prefijo){
		
		String codigo = StringUtils.leftPad(numero.toString(), longitud, cadenaRelleno);
		codigo = prefijo.concat(codigo);
		
		return codigo;
	}
	
	/**
	 * 
	 * @param string cadena a la cual se va quitar espacios al inicio y al final.
	 * Ademas, se va a pasar a mayusculas todas la letras de la cadena y se reemplazaran
	 * los espacios en blanco intermedios por guiones bajos.
	 * @return   string
	 */
	public static String cleanCodeString(String string){
		
		string = string.trim().toUpperCase();
		string = string.replaceAll(CLPConstantes.ESPACIO_BLANCO, CLPConstantes.CARACTER_GUION_BAJO);
		return string;
	}
}
