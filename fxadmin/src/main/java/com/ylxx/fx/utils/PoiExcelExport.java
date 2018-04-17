package com.ylxx.fx.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class PoiExcelExport<T> {
    // Excel导出名称
    private String fileName;
    // list集合
    private List<T> list;
    private List<Table> tableList;
    private HttpServletResponse response;
    // 构造函数
    public PoiExcelExport(String fileName, List<Table> tableList, List<T> list, HttpServletResponse response) {
        this.fileName = fileName;
        this.list = list;
        this.response = response;
        this.tableList = tableList;
    }
    public void exportExcel() throws IllegalArgumentException, IllegalAccessException, IOException {
    	String[] heads = null;
        String[] cols = null;
    	if(tableList!=null && tableList.size()>0){
    		heads = new String [tableList.size()];
    		cols = new String [tableList.size()];
    		for (int i = 0; i < tableList.size(); i++) {
             	heads[i]=tableList.get(i).getName();
             	cols[i]= tableList.get(i).getValue();
     		}
    	}
        HSSFWorkbook hssfworkbook = new HSSFWorkbook(); // 创建一个excel对象
        if(list!=null && list.size()>0){
	        for (int i = 0; i <= (list.size() /100); i++) {
	            HSSFSheet hssfsheet = hssfworkbook.createSheet(); // 工作表
	            for (int j = 0; j < heads.length; j++) {
	            	hssfsheet.setColumnWidth(j, 30 * 256);
				}
	            // 工作表名称
	            HSSFCellStyle cellStyle = hssfworkbook.createCellStyle(); 
	            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
	            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
	            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
	            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
	            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 
	            
	            hssfworkbook.setSheetName(i, fileName.replace(".xls", "") + "(第" + (i + 1) + "页)");
	            int beginRows = 100 * i;
	            int endRows = (list.size() > 100 * (i + 1)) ? 100 * (i + 1) - 1 : list.size() - 1;
	            HSSFRow hssfrowHead = hssfsheet.createRow(i);
	            hssfrowHead.setHeight((short) (45*20));
	            // 输出excel 表头
	            if (heads != null && heads.length > 0) {
	                for (int h = 0; h < heads.length; h++) {
	                    HSSFCell hssfcell = hssfrowHead.createCell(h,Cell.CELL_TYPE_STRING);
	                    hssfcell.setCellValue(heads[h]);
	                    hssfcell.setCellStyle(cellStyle);
	                }
	            }
	            // 输出excel 数据
	            for (int curRow = beginRows; curRow <= endRows; curRow++) {
	                // 获取数据
	                BeanToMap<T> btm = new BeanToMap<T>();
	                Map<String,Object> hm = btm.getMaps(list.get(curRow),cols);
	                // 创建excel行 表头1行 导致数据行数 延后一行
	                HSSFRow hssfrow = hssfsheet.createRow(curRow % 100 + 1);
	                hssfrow.setHeight((short) (20*20));
	                // 读取数据值
	                for (int k = 0; k < cols.length; k++) {
	                    HSSFCell hssfcell = hssfrow.createCell(k);
	                    hssfcell.setCellValue(hm.get(cols[k])==null?"":hm.get(cols[k]).toString());
	                }
	            }
	        }
        }else{
        	HSSFSheet hssfsheet = hssfworkbook.createSheet(); // 工作表
        	if(heads!=null && heads.length>0){
        		for (int j = 0; j < heads.length; j++) {
                	hssfsheet.setColumnWidth(j, 30 * 256);
    			}
                // 工作表名称
                HSSFCellStyle cellStyle = hssfworkbook.createCellStyle(); 
                cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
                cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
                cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
                cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 
                
                hssfworkbook.setSheetName(0, fileName.replace(".xls", "") + "(第" + (0 + 1) + "页)");
                int beginRows = 10000 * 0;
                int endRows = 1;
                HSSFRow hssfrowHead = hssfsheet.createRow(0);
                hssfrowHead.setHeight((short) (45*20));
                // 输出excel 表头
                if (heads != null && heads.length > 0) {
                    for (int h = 0; h < heads.length; h++) {
                        HSSFCell hssfcell = hssfrowHead.createCell(h,Cell.CELL_TYPE_STRING);
                        hssfcell.setCellValue(heads[h]);
                        hssfcell.setCellStyle(cellStyle);
                    }
                }
        	}
        }
        // excel生成完毕，写到输出流
        if(list == null || (list !=null && list.size()<500)) {
        	try {
        		String filename = URLEncoder.encode(fileName+".xls", "utf-8");
                response.setHeader("Content-Disposition", "attachment; filename =" + filename + ";filename*=utf-8''" + filename);
                OutputStream out = response.getOutputStream();
                hssfworkbook.write(out);
                out.flush();
                out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else if(list !=null && list.size()>=500) {
        	ZipOutputStream zipOutputStream = null;
            DataOutputStream dataOutputStream = null;
            try {
                String filename = URLEncoder.encode(fileName+".zip", "utf-8");
                response.setHeader("Content-Disposition", "attachment; filename =" + filename + ";filename*=utf-8''" + filename);
                zipOutputStream = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
                zipOutputStream.setMethod(ZipOutputStream.DEFLATED);
                zipOutputStream.putNextEntry(new ZipEntry( fileName+".xls"));
                dataOutputStream = new DataOutputStream(zipOutputStream);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                hssfworkbook.write(bos);
                byte[] bytes = bos.toByteArray();
                dataOutputStream.write(bytes);
                zipOutputStream.closeEntry();
            } catch (IOException e) {
            	 e.printStackTrace();
            } finally {
            	dataOutputStream.close();
            	zipOutputStream.close();
            }
        }
        
    }
}
