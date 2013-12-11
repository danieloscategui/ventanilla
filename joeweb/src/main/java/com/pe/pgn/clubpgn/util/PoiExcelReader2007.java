package com.pe.pgn.clubpgn.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class PoiExcelReader2007 {

	
	public static List getDataSheetFromWorkbook(String pathFile, int numberColumns, 
			int numberSheet, boolean withHeader) throws IOException {		
		
    	Workbook workbook = openExcelFile(pathFile);
    	Sheet sheet = workbook.getSheetAt(numberSheet);
				
    	List dataSheet = new ArrayList();
		for(Row row : sheet){
			
			List dataRow = getRowFromSheet(workbook, sheet, row, numberColumns);
			if(!dataRow.isEmpty() && !isWhiteRow(dataRow)) {
				
				dataSheet.add(dataRow);
			}		
		}
		
		if(dataSheet != null && !dataSheet.isEmpty()) {
			
			if(!withHeader){
				dataSheet.remove(0);
			}			
			return dataSheet;
		}			
		return null;   
	}	
	
	public static Workbook openExcelFile(String pathFile) throws IOException
	{
		FileInputStream fin = new FileInputStream(pathFile);		 
		Workbook workbook = new XSSFWorkbook(fin);		 
		return workbook;		
	}
	
	public static List getRowFromSheet(Workbook workbook, Sheet sheet, 
			Row row, int numberColumns)
	{
		List dataRow = new ArrayList();
		Cell cell = null;
		for(int i = 0; i < numberColumns; i++)
		{			
			cell = row.getCell(i);			
			if(cell == null)
			{
				dataRow.add("");
			}
			else 
			{
				setValueFromCell(cell, dataRow);						
			}					
		}
		return dataRow;
	}	
	
	public static void setValueFromCell(Cell cell, List dataRow)
	{		
		switch(cell.getCellType()) {	
		
			case Cell.CELL_TYPE_STRING:
					dataRow.add(cell.getRichStringCellValue().getString());
					break;
			case Cell.CELL_TYPE_NUMERIC: 
					if(DateUtil.isCellDateFormatted(cell)) {
						dataRow.add(cell.getDateCellValue());
	//					Timestamp cal = new Timestamp(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).getTime());
					} else {
						dataRow.add(cell.getNumericCellValue());
					}
					break;
			case Cell.CELL_TYPE_BOOLEAN:
					dataRow.add(cell.getBooleanCellValue());
					break;
			case Cell.CELL_TYPE_FORMULA:
	//				double valueDL = evaluator.evaluate(cell).getNumberValue();
	//				row.add(String.valueOf(valueDL));						
	//				break;		
					dataRow.add(cell.getCellFormula());
					break;
			case Cell.CELL_TYPE_BLANK:
					//cell type blank							
					dataRow.add("");		
					break;			
			default:
					break;
		}    
	}	
	
	public static boolean isWhiteRow(List row)
	{		
		for(Iterator iter = row.iterator(); iter.hasNext(); ) {
			
			String cell = String.valueOf(iter.next());
			if(cell != null && cell.length() > 0 && !"null".equals(cell)){				
				return false;
			}
		}		
		return true;
	}
}