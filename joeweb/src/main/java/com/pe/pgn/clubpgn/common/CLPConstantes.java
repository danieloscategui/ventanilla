package com.pe.pgn.clubpgn.common;

public class CLPConstantes {

	public static final int CANTIDAD_DECIMALES = 2;
	
	public static final int LIMITE_CADENA = 10;
	
	public static final String PUNTOS_SUSPENSIVOS = "...";
	
	public static final int MAXIMO_RESULTADO_INICIAL = 30;
	public static final int MAXIMO_RESULTADO_INICIAL_EXTRA = 100;
	public static final String ITEM_DEFAULT = "----";
	
	public static final String ESPACIO_BLANCO = " ";
	public static final String ESPACIO_VACIO = "";
	
	public static final String CARACTER_GUION_BAJO = "_";	
	public static final String CARACTER_GUION=" - ";
	public static final String CARACTER_GUION_NO_ESPACIOS = "-"; 
	public static final String CARACTER_COMA = ",";
	public static final String CARACTER_PORCENTAJE = "%";
	
	public static final String FLAG_YES = "Si";
	
	public static final String FLAG_NO = "No";

	public static final Integer LIMITE_CODIGO_TARJETA_PERSONA = 12;
	public static final int LONGITUD_MAXIMA_NUMERO_TARJETA = 11;
	
	public static final int LIMITE_CODIGO_TARJETA_EMPRESA = 7;
	public static final int LONGITUD_MAXIMA_NUMERO_TARJETA_EMPRESA = 6;
	
	public static final String CODIGO_TARJETA_PERSONA = "1";
	
	public static final String CODIGO_RELLENO_TARJETA_PERSONA = "0";

	public static final String ACTIVO="Activo";

	public static final String NO_ACTIVO="No Activo";
	
	public static final String ZERO = "0";
	public static final String UNO = "1";

	public static final String PREFIJO_TARJETA_PERSONA = "1";
	public static final String PREFIJO_TARJETA_EMPRESA = "2";
	
	public static final String ST_VERDADERO = "Y";
	public static final String ST_FALSO = "N";
	
	public static final int CAMPO_RANGO_EXISTENTE = 1;
	public static final int CAMPO_MAYOR = 2;
	
	public static final String FORMATO_FECHA_CLUB_PGN_SHORT = "dd-MM-yyyy";
	public static final String FORMATO_FECHA_CLUB_PGN_BD = "dd/MM/yyyy HH24:MI:SS";
	public static final String FORMATO_FECHA_CLUB_PGN = "dd/MM/yyyy' 'HH:mm:ss";
	public static final String FORMATO_FECHA_CLUB_PGN_MINUTO = "dd/MM/yyyy' 'HH:mm";
	
	public static final String DATE_PATTERN = "dd/MM/yyyy";
	public static final String DATE_PATTERN_LIQ = "yyyyMMdd";
	public static final Integer	MAYORIA_DE_EDAD = 18;

	public static final String INDICADOR_TARJETA_1 = "inserccion_tarjeta_primera_vez";
	public static final String INDICADOR_TARJETA_2 = "tenia_tarjeta_pero_lo_borro";
	
	public static final String FLAG_INSERT  = "insert";	
	public static final String FLAG_UPDATE  = "update";	
	public static final String FLAG_DELETE  = "delete";	
	public static final String FLAG_WARNING = "warning";
	
	public static final String VENTA_ACTIVA= "A";
	public static final String VENTA_INACTIVA= "I";
	public static final String VENTA_MANUAL= "M";
	public static final String VENTA_ST_MODIFICADO= "1";


	public static final String ST_ONLINE = "N"; //Se grabo On Line
	public static final String ST_OFFLINE = "F"; //Se grabo Off Line
	
	public static final int LONGITUD_NUMERO_CODIGO_ARTICULO = 3;
	
	public static final String ST_NO_MANEJA_STOCK = "NM";
	public static final String ST_MANEJA_STOCK_PUNTO_CANJE = "PC";
	public static final String ST_MANEJA_STOCK_ALMACEN_PRINCIPAL = "AP";
	public static final String ST_PREMIO_INSTANTANEO_NORMAL = "IN";
	public static final String ST_PREMIO_INSTANTANEO_VALE = "IV";
	public static final String ST_ALMACEN_PRINCIPAL = "AP";
	public static final String ST_ALMACEN_VIRTUAL = "AV";
	public static final String ST_PUNTO_CANJE = "PC";
	
	
	public static final String LIMA = "Lima";
	
	
	/*********************************
	* Constantes para tipo documento *
	**********************************/
	public static final String ST_DOCUMENTO_RESERVA = "RS";
	public static final String ST_DOCUMENTO_CANJE 	= "CJ";
	public static final String ST_DOCUMENTO_CANJE_ISLA 	= "CI";
	public static final String ST_DOCUMENTO_ASIGNACION_ARTICULO_A_CATALOGO = "AC";
	public static final String ST_DOCUMENTO_INGRESO_ARTICULO = "IA";
	public static final String ST_DOCUMENTO_DEVOLUCION_PROVEEDOR = "DP";
	public static final String ST_DOCUMENTO_DEVOLUCION_CLIENTE = "DC";
	public static final String ST_DOCUMENTO_TRANSFERENCIA 	= "TS";
	public static final String ST_DOCUMENTO_BAJA ="DB";
	
	/************************************
	 * Constantes para tipo movimientos *
	 ************************************/
	
	public static final String 	ASIGNACION_STOCK_A_CATALOGO_DE_ARTICULO_QUE_NO_MANEJA_STOCK	=	"ASIGNACION_STOCK_A_CATALOGO_DE_ARTICULO_QUE_NO_MANEJA_STOCK"; 
	public static final String 	ASIGNACION_STOCK_A_CATALOGO_DE_ARTICULO_QUE_MANEJA_STOCK	=	"ASIGNACION_STOCK_A_CATALOGO_DE_ARTICULO_QUE_MANEJA_STOCK";
	public static final String 	DESASIGNACION_STOCK_A_CATALOGO_DE_ARTICULO_QUE_NO_MANEJA_STOCK	=	"DESASIGNACION_STOCK_A_CATALOGO_DE_ARTICULO_QUE_NO_MANEJA_STOCK";
	public static final String 	DESASIGNACION_STOCK_A_CATALOGO_DE_ARTICULO_QUE_MANEJA_STOCK	=	"DESASIGNACION_STOCK_A_CATALOGO_DE_ARTICULO_QUE_MANEJA_STOCK";
	public static final String 	INGRESO_DE_ARTICULO	=	"INGRESO_DE_ARTICULO";
	public static final String 	CANJE_DE_PREMIO	=	"CANJE_DE_PREMIO";
	public static final String 	PREMIO_COMPROMETIDO_POR_CANJE	=	"PREMIO_COMPROMETIDO_POR_CANJE";
	public static final String 	DEVOLUCION_DE_PREMIO	=	"DEVOLUCION_DE_PREMIO";
	public static final String 	ANULACION_DE_RESERVA_DE_PREMIO	=	"ANULACION_DE_RESERVA_DE_PREMIO";
	public static final String 	DEVOLUCION_DE_ARTICULO_A_PROVEEDOR	=	"DEVOLUCION_DE_ARTICULO_A_PROVEEDOR";
	public static final String 	TRANSFERENCIA_ENTRE_ALMACENES	=	"TRANSFERENCIA_ENTRE_ALMACENES";
	public static final String 	TRANSFERENCIA_ENTRE_CATALOGOS	=	"TRANSFERENCIA_ENTRE_CATALOGOS";
	public static final String 	DAR_BAJA_PREMIOS_CATALOGO	=	"DAR_BAJA_PREMIOS_CATALOGO";
	
	
	/*********************************
	* Constantes para tipo linea credito *
	**********************************/
	public static final String ST_PREPAGO = "R";
	public static final String ST_POST_PAGO_CERRADA 	= "A";
	public static final String ST_POST_PAGO_ABIERTA 	= "C";
	
	/*********************************
	* Constantes para tipo tope *
	**********************************/	 	
	public static final String ST_TOPE_VERDE 	= "V";
	public static final String ST_TOPE_AMBAR 	= "A";
	public static final String ST_TOPE_ROJO 	= "R";
	
	
	public final static String UPLOAD_DIR = "UPLOAD_DIR";
	
	public final static int NUMERO_COLUMNAS_ARCHIVO_FLOTAS = 4; 
	
	/**
	 * Indicadores de Tarjeta Empresa
	 */
	public final static int TARJETA_EMPRESA_VALIDA = 0;
	public final static int TARJETA_EMPRESA_NO_EXISTE_O_NO_HABILITADA = 1;
	public final static int TARJETA_EMPRESA_USADA = 2;
	public final static int TARJETA_EMPRESA_BLOQUEADA = 3;
	
	/* Constantes para tipo incidencia *
	**********************************/	 	
	public static final int ST_INCIDENCIA_PUNTO_PENALIZACION 	= 1;
	public static final int ST_INCIDENCIA_PUNTO_BONIFICACION 	= 2;
	
	public static final int LONGITUD_MAXIMA_PLACA = 15;
	public static final int LONGITUD_MAXIMA_OBSERVACIONES = 255;

	public static final String INIROW = "(fila ";
	public static final String FINROW = ")";
	
	public static final int LONGITUD_PARRAFO_TICKET = 40;
	public static final int LONGITUD_PARRAFO_TICKET_MENOS_UNO = 39;
	
	public static final String FILTRO_ID_COMODIN = "-1";

	public static final String ST_OK 		= "1";	
	public static final String ST_ERROR 	= "0";
	
	public static final String ST_ROJO  = "R";
	public static final String ST_VERDE = "V";
	public static final String ST_AMBAR = "A";
	
	public static final String CARACTER_TAB = "\t";
	public static final String CARACTER_ENTER = "\n";
	public static final String EN_FLOTA = " en la flota ";
	
	public static final String NO_INGRESADO = "noingresado";
	public static final String INGRESADO = "ingresado";
	
	public static final String LEFT_JOIN = "left";
	
	public static final String MSG = "msg";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	
	public static final String WEBSERVICE = "W";
	public static final String MANUAL = "M";
	public static final String DE_WEBSERVICE = "Servicio Web";
	public static final String DE_REGULAR = "Regular";
	public static final String DE_MANUAL = "Manual";

public static final int INDICADOR_NUEVO_CLIENTE = 1;
	public static final int INDICADOR_CLIENTE_NO_ES_DE_EMPRESA_AFILIADORA = 2;
	public static final int INDICADOR_CLIENTE_ES_DE_EMPRESA_AFILIADORA = 3;

}
