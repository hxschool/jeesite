package com.thinkgem.jeesite.common.utils;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.Region;
import org.springframework.util.StringUtils;

public class POIUtils {
//	/**
//	 * 把一个excel中的cellstyletable复制到另一个excel，这里会报错，不能用这种方法，不明白呀？？？？？
//	 * @param fromBook
//	 * @param toBook
//	 */
//	public static void copyBookCellStyle(HSSFWorkbook fromBook,HSSFWorkbook toBook){
//		for(short i=0;i<fromBook.getNumCellStyles();i++){
//			HSSFCellStyle fromStyle=fromBook.getCellStyleAt(i);
//			HSSFCellStyle toStyle=toBook.getCellStyleAt(i);
//			if(toStyle==null){
//				toStyle=toBook.createCellStyle();
//			}
//			copyCellStyle(fromStyle,toStyle);
//		}
//	}
	public static CellStyle formatCell(HSSFWorkbook wb) {
		CellStyle style = wb.createCellStyle();
		HSSFFont hssfFont = wb.createFont();
		hssfFont.setFontName("宋体");
		style.setFont(hssfFont);

		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		return style;
	}
	/**
	 * 复制一个单元格样式到目的单元格样式
	 * @param fromStyle
	 * @param toStyle
	 */
	public static void copyCellStyle(HSSFCellStyle fromStyle,
			HSSFCellStyle toStyle) {
		toStyle.setAlignment(fromStyle.getAlignment());
		//边框和边框颜色
		toStyle.setBorderBottom(fromStyle.getBorderBottom());
		toStyle.setBorderLeft(fromStyle.getBorderLeft());
		toStyle.setBorderRight(fromStyle.getBorderRight());
		toStyle.setBorderTop(fromStyle.getBorderTop());
		toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
		toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
		toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
		toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());
		
		//背景和前景
		toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
		toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());
		
		toStyle.setDataFormat(fromStyle.getDataFormat());
		toStyle.setFillPattern(fromStyle.getFillPattern());
//		toStyle.setFont(fromStyle.getFont(null));
		toStyle.setHidden(fromStyle.getHidden());
		toStyle.setIndention(fromStyle.getIndention());//首行缩进
		toStyle.setLocked(fromStyle.getLocked());
		toStyle.setRotation(fromStyle.getRotation());//旋转
		toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
		toStyle.setWrapText(fromStyle.getWrapText());
		
	}
	/**
	 * Sheet复制
	 * @param fromSheet
	 * @param toSheet
	 * @param copyValueFlag
	 */
	public static void copySheet(HSSFWorkbook wb,HSSFSheet fromSheet, HSSFSheet toSheet,
			boolean copyValueFlag) {
		//合并区域处理
		mergerRegion(fromSheet, toSheet);
		for (Iterator rowIt = fromSheet.rowIterator(); rowIt.hasNext();) {
			HSSFRow tmpRow = (HSSFRow) rowIt.next();
			HSSFRow newRow = toSheet.createRow(tmpRow.getRowNum());
			//行复制
			copyRow(wb,tmpRow,newRow,copyValueFlag);
		}
	}
	/**
	 * 行复制功能
	 * @param fromRow
	 * @param toRow
	 */
	public static void copyRow(HSSFWorkbook wb,HSSFRow fromRow,HSSFRow toRow,boolean copyValueFlag){
		for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext();) {
			HSSFCell tmpCell = (HSSFCell) cellIt.next();
			HSSFCell newCell = toRow.createCell(tmpCell.getCellNum());
			copyCell(wb,tmpCell, newCell, copyValueFlag);
		}
	}
	/**
	* 复制原有sheet的合并单元格到新创建的sheet
	* 
	* @param sheetCreat 新创建sheet
	* @param sheet      原有的sheet
	*/
	public static void mergerRegion(HSSFSheet fromSheet, HSSFSheet toSheet) {
	   int sheetMergerCount = fromSheet.getNumMergedRegions();
	   for (int i = 0; i < sheetMergerCount; i++) {
	    Region mergedRegionAt = fromSheet.getMergedRegionAt(i);
	    toSheet.addMergedRegion(mergedRegionAt);
	   }
	}
	/**
	 * 复制单元格
	 * 
	 * @param srcCell
	 * @param distCell
	 * @param copyValueFlag
	 *            true则连同cell的内容一起复制
	 */
	public static void copyCell(HSSFWorkbook wb,HSSFCell srcCell, HSSFCell distCell,
			boolean copyValueFlag) {
		HSSFCellStyle newstyle=wb.createCellStyle();
		copyCellStyle(srcCell.getCellStyle(), newstyle);
		//distCell.setEncoding(srcCell.getEncoding());
		//样式
		distCell.setCellStyle(newstyle);
		//评论
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		// 不同数据类型处理
		int srcCellType = srcCell.getCellType();
		distCell.setCellType(srcCellType);
		if (copyValueFlag) {
			if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {
				if (HSSFDateUtil.isCellDateFormatted(srcCell)) {
					distCell.setCellValue(srcCell.getDateCellValue());
				} else {
					distCell.setCellValue(srcCell.getNumericCellValue());
				}
			} else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {
				distCell.setCellValue(srcCell.getRichStringCellValue());
			} else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {
				// nothing21
			} else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {
				distCell.setCellValue(srcCell.getBooleanCellValue());
			} else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {
				distCell.setCellErrorValue(srcCell.getErrorCellValue());
			} else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {
				distCell.setCellFormula(srcCell.getCellFormula());
			} else { // nothing29
			}
		}
	}
	
	public static String getCell(Cell cell) {
		String value = "";
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_NUMERIC:
				value = String.valueOf(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				break;
			case Cell.CELL_TYPE_FORMULA:
				break;
			case Cell.CELL_TYPE_ERROR:
				break;
			default:
				value = cell.getStringCellValue();
				break;
		}
		return value;
	}
	
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static Font getFont(Workbook wb) {
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 16);
		font.setFontName("黑体");
		font.setUnderline(Font.U_SINGLE); // 下划线
		return font;
	}
	
	public static String format(String str) {
		if(StringUtils.isEmpty(str)) {
			return "";
		}
		 String reg = "[^\u4e00-\u9fa5]";  
		 //《生活趣味“化”*》
		return str.replace("*", "").replace("\"", "").replace("“", "").replace("”", "").replace("《", "").replace("》", "");
	}
}