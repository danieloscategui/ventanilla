package com.pe.pgn.clubpgn.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcelWriter2007 {

	/**
	 * @return A interface to army a Excel 2007 File
	 */
	public static Workbook createWorkbook(){
		
		return new XSSFWorkbook();
	}
	
	/**
	 * 
	 * @param workbook to create a sheet
	 * @param sheetName name for sheet 
	 * @return sheet to add many rows in file
	 */
	public static Sheet createSheet(Workbook workbook, String sheetName){
		
		return workbook.createSheet(sheetName);		
	}
	
	public static Font createFont(Workbook workbook, short boldweigthValue, short colorValue,
			short fontHeightValue, String fontNameValue, boolean isItalic, boolean isStrikeOut, 
			boolean hasTypeOffset, short typeOffsetValue, boolean hasUnderline,	byte underlineValue){
		
		Font font = workbook.createFont();	
		
		font.setBoldweight(boldweigthValue);
		font.setColor(colorValue);
		font.setFontHeightInPoints(fontHeightValue);
		font.setFontName(fontNameValue);
		font.setItalic(isItalic);
		font.setStrikeout(isStrikeOut);
		
		if(hasTypeOffset){
			font.setTypeOffset(typeOffsetValue);
		}
		
		if(hasUnderline){
			font.setUnderline(underlineValue);	
		}
				
		return font;		
	}
	
	public static Row createRow(Sheet sheet, int numRow){
		
		return sheet.createRow((short)numRow);
	}
	
	public static Cell createTextCell(Row row, CellStyle cellStyle, int numCell, String value){
		
		Cell cell = row.createCell(numCell);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);		
		return cell;
	}
}
