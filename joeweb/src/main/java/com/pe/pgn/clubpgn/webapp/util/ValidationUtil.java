package com.pe.pgn.clubpgn.webapp.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.FieldChecks;

import com.pe.pgn.clubpgn.common.CLPConstantes;


/**
 * ValidationUtil Helper class for performing custom validations that
 * aren't already included in the core Commons Validator.
 *
 * <p>
 * <a href="ValidationUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class ValidationUtil {
    //~ Methods ================================================================

    /**
     * Validates that two fields match.
     * @param bean
     * @param va
     * @param field
     * @param errors
     */
    public static boolean validateTwoFields(Object bean, ValidatorAction va,
                                            Field field, Errors errors) {
        String value =
            ValidatorUtils.getValueAsString(bean, field.getProperty());
        String sProperty2 = field.getVarValue("secondProperty");
        String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);

        if (!GenericValidator.isBlankOrNull(value)) {
            try {
                if (!value.equals(value2)) {
                    FieldChecks.rejectValue(errors, field, va);
                    return false;
                }
            } catch (Exception e) {
                FieldChecks.rejectValue(errors, field, va);
                return false;
            }
        }

        return true;
    }
    
    public static String getStrByBooleano(boolean b){
		if(b){
			return CLPConstantes.ST_VERDADERO;
		}else{
			return CLPConstantes.ST_FALSO;
		}		
	}
	
	public static boolean isValidateString(String str){
		
		if(str != null && !"null".equalsIgnoreCase(str) && !"".equalsIgnoreCase(str)){
			return true;
		}else{
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static String convertElementsListToString(List list,String key){
		
		String strAcum = "";
		if(validateList(list)){
			
			for (int i = 0; i < list.size(); i++) {
				
				Map obj = (HashMap)list.get(i);
				strAcum = strAcum + obj.get(key)+",";
			}
			
			if(strAcum.length() > 0){
				strAcum = strAcum.substring(0, strAcum.length()-1);
			}
			
			return strAcum;
		}		
		return "";
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean validateList(List list){
		
		if(list != null && list.size() > 0){
			return true;
		}		
		return false;
	}
	
	public static boolean isOlder(Calendar calendar){
		
		int actual = Calendar.getInstance().get(Calendar.YEAR) - CLPConstantes.MAYORIA_DE_EDAD;
		int nacio  = calendar.get(Calendar.YEAR);
		
		if (actual<nacio){
			return false;
		}
		return true;
	}
	
	public static boolean isNumber(String str){
		try{
			Double.parseDouble(str.trim());
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean isAlphanumeric(String campo){
		
		boolean isOk   = true;
		String validos = "abcdefghijklmnopqrstuvwxyz0123456789"; 
    	String letra;
       	 
       for (int i=0; i < campo.length(); i++){
        
    	   letra = String.valueOf(campo.charAt(i)).toLowerCase();
           if (validos.indexOf(letra) == -1)
           isOk = false; 
       } 

       return isOk;
	}
	
	public static String getQueryDefault(){
		return " update clpm_stock_almacen_catalogo " +
		"set co_usuario_modificador = co_usuario_modificador where id = -1";		
	}
}
