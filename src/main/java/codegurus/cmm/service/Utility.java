package codegurus.cmm.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;


public class Utility {

	private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);
	
	public static List<Map<String, Object>> excelFileRead(FileVO vo, InputStream inputStream, String columnNames[], EgovExcelService excelService) throws Exception  {
		for(int i = 0; i < columnNames.length; i++) {
//			columnNames[i] = tranCamel(columnNames[i].toLowerCase());
			columnNames[i] = columnNames[i].toLowerCase();
		}
		
		if(vo.getFileExtsn().equals("xlsx")) {
			return excelFileReadX(inputStream, columnNames, excelService);
		} else if(vo.getFileExtsn().equals("xls")) {
			return excelFileReadN(inputStream, columnNames, excelService);
		}
		
		return null;
	}
	
	public static List<Map<String, Object>> excelFileReadX(InputStream inputStream, String columnNames[], EgovExcelService excelService) throws Exception  {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		XSSFWorkbook hssfWB = new XSSFWorkbook(inputStream);
		
		//hssfWB.getNumberOfSheets() ?????? ????????????
		
		XSSFSheet sheet  = hssfWB.getSheetAt(0);  //?????? ????????????
        XSSFRow   row    = sheet.getRow(1); //row ????????????
        int cellCount      = row.getPhysicalNumberOfCells(); // cell Cnt
        int rowsCnt=sheet.getPhysicalNumberOfRows(); //??? ?????? ????????????

        for(int j=1; j < rowsCnt; j++){ //row ??????
        	Map<String, Object> map = new HashMap<String, Object>();
            XSSFRow itemRow=sheet.getRow(j); //itemRow ????????????
            if(itemRow!=null){
            	int cells = itemRow.getPhysicalNumberOfCells(); //cell ?????? ????????????
	            for(int i = 0; i < columnNames.length; i++) {
	                XSSFCell cell = null;
	            	cell = itemRow.getCell(i);  //????????????
	            	if(cell != null){
	            		String value = "";
	            		switch(cell.getCellType()) {
	                        case XSSFCell.CELL_TYPE_FORMULA:
		            			map.put(columnNames[i], cell.getCellFormula());
	                            break;
		            		case XSSFCell.CELL_TYPE_NUMERIC:
		            			double doubleValue = cell.getNumericCellValue();
//		            			value = String.valueOf(doubleValue);
//		            			if(value.endsWith(".0")) {
//		            				value = value.replace(".0", "");
//		            			}
		            			value = new DecimalFormat("###.#####").format(doubleValue);
		            			map.put(columnNames[i], value);
		            			break;
		            		case XSSFCell.CELL_TYPE_STRING:
		            			map.put(columnNames[i], cell.getStringCellValue());
		            			break;
                            case XSSFCell.CELL_TYPE_BLANK:
                            	map.put(columnNames[i], value);
                                break;
	                        case XSSFCell.CELL_TYPE_BOOLEAN:
	                            boolean booleanValue = cell.getBooleanCellValue();
	                            if(booleanValue) {
	                            	value = "true";
	                            } else {
	                            	value = "false";
	                            }
		            			map.put(columnNames[i], value);
	                            break;
	            		}
	            	}
	            }
            }
                
            list.add(map);
        }
		
		return list;
	}
	
	public static List<Map<String, Object>> excelFileReadN(InputStream inputStream, String columnNames[], EgovExcelService excelService) throws Exception  {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		HSSFWorkbook hssfWB = (HSSFWorkbook) excelService.loadWorkbook(inputStream);
		
		//hssfWB.getNumberOfSheets() ?????? ????????????
		
		HSSFSheet sheet  = hssfWB.getSheetAt(0);  //?????? ????????????
		HSSFRow   row    = sheet.getRow(1); //row ????????????
        int cellCount      = row.getPhysicalNumberOfCells(); // cell Cnt
        int rowsCnt=sheet.getPhysicalNumberOfRows(); //??? ?????? ????????????

        for(int j=1; j < rowsCnt; j++){ //row ??????
        	Map<String, Object> map = new HashMap<String, Object>();
        	HSSFRow itemRow=sheet.getRow(j); //itemRow ????????????
            if(itemRow!=null){
            	int cells = itemRow.getPhysicalNumberOfCells(); //cell ?????? ????????????
	            for(int i = 0; i < columnNames.length; i++) {
	            	HSSFCell cell = null;
	            	cell = itemRow.getCell(i);  //????????????
	            	if(cell!=null){
	            		String value = "";
	            		switch(cell.getCellType()) {
	                        case HSSFCell.CELL_TYPE_FORMULA:
		            			map.put(columnNames[i], cell.getCellFormula());
	                            break;
		            		case HSSFCell.CELL_TYPE_NUMERIC:
		            			double doubleValue = cell.getNumericCellValue();
//		            			value = String.valueOf(doubleValue);
//		            			if(value.endsWith(".0")) {
//		            				value = value.replace(".0", "");
//		            			}
		            			value = new DecimalFormat("###.#####").format(doubleValue);
		            			map.put(columnNames[i], value);
		            			break;
		            		case HSSFCell.CELL_TYPE_STRING:
		            			map.put(columnNames[i], cell.getStringCellValue());
		            			break;
                            case HSSFCell.CELL_TYPE_BLANK:
                            	map.put(columnNames[i], value);
                                break;
	                        case HSSFCell.CELL_TYPE_BOOLEAN:
	                            boolean booleanValue = cell.getBooleanCellValue();
	                            if(booleanValue) {
	                            	value = "1";
	                            } else {
	                            	value = "0";
	                            }
		            			map.put(columnNames[i], value);
	                            break;
	            		}
	            	}
	            }
            }
                
            list.add(map);
        }
		
		return list;
	}
	
	public static String excelFileCreate(String name, List<Map<String, Object>> list, String titles[], String columnNames[], EgovIdGnrService idgenService, EgovFileMngService fileMngService) throws Exception  {
	    return excelFileCreate(name, list, titles, columnNames, null, idgenService, fileMngService);
	}
	
	public static String excelFileCreate(String name, List<Map<String, Object>> list, String titles[], String columnNames[], double columnSizes[], EgovIdGnrService idgenService, EgovFileMngService fileMngService) throws Exception  {
        
		String orginFileName = name + ".xlsx";
		long fileKey = 0;
		String storePathString = EgovProperties.getProperty("Globals.fileStorePath");

		File saveFolder = new File(EgovWebUtil.filePathBlackList(storePathString));

		if (!saveFolder.exists() || saveFolder.isFile()) {
			//2017.03.03 	????????? 	???????????????(ES)-???????????? ?????? ??????[CWE-253, CWE-440, CWE-754]
			if (saveFolder.mkdirs()){
				LOGGER.debug("[file.mkdirs] saveFolder : Creation Success ");
			}else{
				LOGGER.error("[file.mkdirs] saveFolder : Creation Fail ");
			}
		}
		
		String newName = "FILE_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + fileKey;
		int index = orginFileName.lastIndexOf(".");
		String fileExt = orginFileName.substring(index + 1);
		
		String filePath = storePathString + File.separator + newName;
		String atchFileIdString = idgenService.getNextStringId();
		
		// Workbook ??????
//		Workbook wb = new HSSFWorkbook(); // Excel 2007 ?????? ??????
		SXSSFWorkbook wb = new SXSSFWorkbook(); // Excel 2007 ??????
     // *** Sheet-------------------------------------------------
        // Sheet ??????
        Sheet sheet1 = wb.createSheet(name);
 
        // ?????? ?????? ??????
        if(columnSizes != null) {
        	int i = 0;
        	for(double size : columnSizes) {
                sheet1.setColumnWidth(i++, (int) (size * 263d));
        	}
        }
        // ----------------------------------------------------------
         
        // *** Style--------------------------------------------------
        // Cell ????????? ??????
//        SXSSFDataFormat format = wb.createDataFormat();
        XSSFCellStyle  styleTop = (XSSFCellStyle) wb.createCellStyle();
//	    style.setDataFormat(format.getFormat("#,###"));
         
        // ??? ??????
//        styleTop.setWrapText(true);
         
        // Cell ??????, ?????? ?????????
        styleTop.setAlignment(CellStyle.ALIGN_CENTER); //????????? ??????
        styleTop.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //?????? ????????? ??????
        styleTop.setFillForegroundColor(new XSSFColor(new byte[] {(byte) 255,(byte) 192,(byte) 0}));
        styleTop.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //????????? ??? (???,???,???,??????)
        styleTop.setBorderRight(XSSFCellStyle.BORDER_THIN);
        styleTop.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//        styleTop.setBorderTop(XSSFCellStyle.BORDER_THIN);
//        styleTop.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        
//        styleTop.setFillPattern(CellStyle.BIG_SPOTS); // ??????
        //?????? ??????
        Font fontTop = wb.createFont();
        fontTop.setFontName("?????? ??????"); //?????????
        fontTop.setFontHeight((short)(12*20)); //?????????
        fontTop.setBoldweight(Font.BOLDWEIGHT_BOLD); //?????? (??????)
        styleTop.setFont(fontTop);
         
        Row row = sheet1.createRow(0);
        row.setHeight((short) (49.5d * 20d));
        
        Cell cell = null;
        
        int i = 0;
        for(String title : titles) {
        	cell = row.createCell(i);
        	cell.setCellValue(title);
        	cell.setCellStyle(styleTop);
        	i++;
        }

        XSSFCellStyle  style = (XSSFCellStyle) wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER); //????????? ??????
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //?????? ????????? ??????
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        Font font = wb.createFont();
        font.setFontName("?????? ??????"); //?????????
        font.setFontHeight((short)(11*20)); //?????????
        style.setFont(font);
        
        i = 1;
        int j = 0;
        for(Map<String, Object> map : list) {
        	row = sheet1.createRow(i);
            row.setHeight((short) (30d * 20d));
        	j = 0;
        	for(String columnName : columnNames) {
        		Object data = map.get(tranCamel(columnName.toLowerCase()));
            	cell = row.createCell(j);
				cell.setCellValue(data == null ? "" : String.valueOf(data));
				cell.setCellStyle(style);
				j++;
        	}
        	i++;
        }
        
        FileOutputStream fos = new FileOutputStream(filePath);
        wb.write(fos);
        fos.close();
        wb.dispose();
        
        
        
        
		/*FileWriter fileWriter = new FileWriter(filePath);
		String output = "<!DOCTYPE html>\r\n" + 
				"	<html lang=\"ko\">\r\n" + 
				"	<head>\r\n" + 
				"	\r\n" + 
				"	</head>\r\n" + 
				"	<body>\r\n" + 
				"	  <table class=\"table table-bordered table-condensed text-center\">\r\n" + 
				"		<thead>\r\n"+
		        "            <th class=\"text-white bg-darkgray\">\r\n" + 
				"				??????\r\n" + 
				"			</th>\r\n";
		for(String title : titles) {
		    output += "            <th class=\"text-white bg-darkgray\">\r\n" + 
		    		"				"+ title +"\r\n" + 
		    		"			</th>\r\n";
		}
		output += "        </thead>\r\n" + 
				"		<tbody>\r\n";
		int i = 1;
		for(Map<String, Object> map : list) {
			output += "				<tr style=\"font-size:11px;\">\r\n" + 
		    		"					<td>" + i++ + "</td>\r\n";
			for(String columnName : columnNames) {
				Object data = map.get(tranCamel(columnName.toLowerCase()));
				output += "                    <td>" + data == null ? "" : data + "</td>\r\n";
			}
			output += 
		    		"				</tr>\r\n";
		}
				
		output += "		</tbody>\r\n" + 
				"	</table>\r\n" + 
				"	</body>\r\n" + 
				"	</html>";
		
		fileWriter.write(output);
		fileWriter.close();*/
        
        File file = new File(filePath);
		long size = file.length();
        
		FileVO fvo;
		fvo = new FileVO();
		fvo.setFileExtsn(fileExt);
		fvo.setFileStreCours(storePathString);
		fvo.setFileMg(size);
		fvo.setOrignlFileNm(orginFileName);
		fvo.setStreFileNm(newName);
		fvo.setAtchFileId(atchFileIdString);
//		fvo.setFileSn(fileKey);
		fvo.setFileSn(String.valueOf(fileKey)); // ????????? ?????? ?????? (20210603 ?????????)

        return fileMngService.insertFileInf(fvo);
	}
	
	public static String tranCamel(String inputStr) {
		int idx = inputStr.indexOf("_");
		if(idx == -1) {
			return inputStr;
		}
		char firstCh = inputStr.charAt(idx + 1);
		String firstStr = String.valueOf(firstCh);
		String str = inputStr.substring(0, idx) + firstStr.toUpperCase() + inputStr.substring(idx + 2);

		return tranCamel(str);
	}
	
	public static Map<String,String> parseData(String prog){
		
		Map<String, String> result = new LinkedHashMap<String,String>();
		
		if(!(prog == null || prog.isEmpty())){
			
			prog = prog.replaceAll("[{}]", " ");
			String[] progEach = prog.split(",");
			
			if(!progEach[0].isEmpty()){
				for(int i = 0; i < progEach.length ; i++){
					String[] element = progEach[i].split(":");
					if(element.length > 1) {
						result.put(element[0].trim(), element[1].trim());
					}
				}	
			}
		}
		
		return result;
	}
	
	public static String diParseData(Map<String, String> data){
		
		String result = "{";
		
		for( String key : data.keySet() ){
			result += key + ":" + data.get(key) + ",";
        }
		
		if(!result.equals("{")) {
			result = result.substring(0, result.length()-1);
		}
		result+= "}";
		return result;
	}
    
	public static String printMap(Map mp) {
		LOGGER.info("testsetset",mp);
		String result = "";
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        result += "&" + pair.getKey() + "=" + pair.getValue();
			LOGGER.info(pair.getKey() + " = " + pair.getValue());
	    }
	    result.substring(1);
	    return result;
	}
	
	public static void printList(List<Object> list) {
		for(int i = 0 ; i < list.size() ; i++) {
			LOGGER.info("list"+i+" : ");
			LOGGER.info(ToStringBuilder.reflectionToString(list.get(i), ToStringStyle.DEFAULT_STYLE));
		}
	}
	
	public static String addData(String list, String value, String separator) {
		String rtn = list;
		if(rtn == null) {
			rtn = "";
		}
		
		String splitData[] = rtn.split(separator);
		
		// ????????????
		for (int i = 0; i < splitData.length; i++) {
			if(splitData[i].equals(value)) {
				// ???????????? ????????? ?????? ?????? ????????????.
				return rtn;
			}
		}
		
		// ???????????? ????????? ???????????????.
		if(rtn.equals("")) {
			rtn = value;
		} else {
			rtn += separator + value;
		}
		
		return rtn;
	}
	
	public static String removeData(String list, String value, String separator) {
		String rtn = "";
		String splitData[] = null;
		
		if(list != null) {
			splitData = list.split(separator);
		}
		
		// ????????????
		boolean isExist = false;
		if(splitData != null) {
			for (int i = 0; i < splitData.length; i++) {
				if(splitData[i].equals(value)) {
					isExist = true;
				} else {
					rtn += splitData[i] + separator;
				}
			}
		}
		
		// ???????????? ????????? ?????? ?????? ??????
		if(!isExist) {
			return list;
		}
		
		// ???????????? ???????????? ??? ??????.
		if(rtn.length() > 0) {
			rtn = rtn.substring(0, rtn.length() - 1);
		}
		
		return rtn;
	}
	
	public static Map<String,String> nameValueListToMap(List<Map<String,String>> target) {
		
		Map<String,String> tmp = new HashMap<String,String>();
		
		for(int i = 0 ; i < target.size() ; i++) {
			Map column = target.get(i);
			tmp.put((String)column.get("name"), (String)column.get("value"));
		}
		
		return tmp;
		
	}
	
	public static List<Map<String,Object>> rowToHierarchy(List<Map<String, Object>> list, String parent, String id) {
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String listParent = "";
		
		if(parent == null) {
			parent = "0";
		}
		
		for(int i = 0 ; i < list.size() ; i++) {
			
			listParent = String.valueOf(list.get(i).get("parentsId"));
			
			if(listParent != null && listParent.equals(parent)) {
				list.get(i).put("child", rowToHierarchy(list, list.get(i).get(id).toString(), id));
				result.add(list.get(i));
			}
			
		}
		
		return result;
	}
	
	public static List<Map<String,Object>> linearLesList(List<Map<String, Object>> list, List<Map<String, Object>> resultList) {
		
		if(resultList == null) {
			resultList = new ArrayList<Map<String, Object>>();
		}
		
		for(int i = 0 ; i < list.size() ; i++) {
			List<Map<String,Object>> child = (List<Map<String,Object>>) list.get(i).get("child");
			
			if(child != null && child.size() > 0) {
				linearLesList(child, resultList);
			}
			
			String connContPath = String.valueOf(list.get(i).get("connContPath"));
			if(connContPath != null && !connContPath.equals("") && !connContPath.equals("null")) {
				resultList.add(list.get(i));
			}
		}
		
		return resultList;
	}
	
	// ?????? ????????? ?????? ?????????????????? ???????????? ??????????????? ???????????? ??????
	public static List<Map<String,Object>> findChilds(List<Map<String, Object>> list, String findValue, String keyName) {
		
		List<Map<String, Object>> child = null;
		List<Map<String, Object>> rtnList = new ArrayList<Map<String,Object>>();
		String checkValue = "";
		
		for(int i = 0 ; i < list.size() ; i++) {
			boolean isAdd = false;
			child = (List<Map<String, Object>>) list.get(i).get("child");
			if(child != null) {
				List<Map<String,Object>> rtn = findChilds(child, findValue, keyName);
				if(rtn != null) {
					list.get(i).put("child", rtn);
					rtnList.add(list.get(i));
					isAdd = true;
				}
			}
			if(!isAdd) {
				checkValue = String.valueOf(list.get(i).get(keyName));
				if(checkValue != null && checkValue.equals(findValue)) {
					rtnList.add(list.get(i));
				}
			}
		}
		if(rtnList.size() > 0) {
			return rtnList;
		}
		return null;
	}
	
	// ?????? ????????? ?????? ?????????????????? ???????????? ??????????????? ???????????? ??????. ??? ????????? ????????????.
	public static Map<String,Object> findChild(List<Map<String, Object>> list, String findValue, String keyName) {
		
		List<Map<String, Object>> child = null;
		String checkValue = "";
		
		for(int i = 0 ; i < list.size() ; i++) {

			checkValue = String.valueOf(list.get(i).get(keyName));
			if(checkValue != null && checkValue.equals(findValue)) {
				return list.get(i);
			}
			child = (List<Map<String, Object>>) list.get(i).get("child");
			if(child != null) {
				Map<String,Object> rtn = findChild(child, findValue, keyName);
				if(rtn != null) {
					list.get(i).put("child", rtn);
					return list.get(i);
				}
			}
			
		}
		
		return null;
	}

	// ??????ID??? ??????????????? name??? ???????????? ???????????? category ??????
	public static String findParentsString(List<Map<String, Object>> list, String findValue, String keyName) {
		String separator = " > ";
		
		List<Map<String, Object>> child = null;
		String checkValue = "";
		
		for(int i = 0 ; i < list.size() ; i++) {

			checkValue = String.valueOf(list.get(i).get(keyName));
			if(checkValue != null && checkValue.equals(findValue)) {
				return String.valueOf(list.get(i).get("name"));
			}
			child = (List<Map<String, Object>>) list.get(i).get("child");
			if(child != null) {
				String rtn = findParentsString(child, findValue, keyName);
				if(rtn != null) {
					return list.get(i).get("name") + separator + rtn;
				}
			}
			
		}
		
		return null;
	}
	
	// ???????????? findValue ?????? ?????? ????????? ????????? ??????
	public static Map<String,Object> findChildOne(List<Map<String, Object>> list, String findValue, String keyName) {
		
		List<Map<String, Object>> child = null;
		String checkValue = "";
		
		for(int i = 0 ; i < list.size() ; i++) {

			checkValue = String.valueOf(list.get(i).get(keyName));
			if(checkValue != null && checkValue.equals(findValue)) {
				return list.get(i);
			}
			child = (List<Map<String, Object>>) list.get(i).get("child");
			if(child != null) {
				Map<String,Object> rtn = findChildOne(child, findValue, keyName);
				if(rtn != null) {
					return rtn;
				}
			}
			
		}
		
		return null;
	}
	
	// ????????? ?????? keyName ?????? separator ????????? ????????? ????????????
	public static String findChildsString(Map<String, Object> map, String keyName) {
		String separator = ", ";
		
		String result = null;
		
		result = String.valueOf(map.get(keyName));
		
		List<Map<String, Object>> childs = null;
		childs = (List<Map<String, Object>>) map.get("child");
		if(childs != null) {
			for(int i = 0 ; i < childs.size() ; i++) {
				String rtn = findChildsString(childs.get(i), keyName);
				if(rtn != null) {
					if(result == null || result.equals("")) {
						result = rtn;
					} else {
						result = result + separator + rtn;
					}
				}
			}
		}
		
		return result;
	}
	
	public static <T> Object convertMapToObject(Map<String, Object> map, Class<T> clazz){
		Object obj = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String jsonString;
		try {
			jsonString = mapper.writeValueAsString(map);
			obj = mapper.readValue(jsonString, clazz);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
    }
	
	public static String convertObjectToJsonString(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
    }
	
	public static List<Map<String, Object>> convertJsonStringToMapList(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<Map<String, Object>> map = null;
		try {
			 map = mapper.readValue(jsonString, new TypeReference<List<Map<Object, Object>>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
    }

	
	public static HashMap<String, Object> convertJsonStringToMap(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		HashMap<String, Object> map = null;
		try {
			 map = mapper.readValue(jsonString, new TypeReference<Map<Object, Object>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
    }
	
	public static String convertDate(Object obj) {
		return  convertDate(obj, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String convertDate(Object obj, String dateFormat) {
		String strObj = String.valueOf(obj);
		Long intObj = Long.parseLong(strObj);
		Date dateObj = new Date(intObj);
		
		return new SimpleDateFormat(dateFormat).format(dateObj);
	}
	
	public static String convertDate(Object obj, String srcPattern, String dstPattern) {
		String strObj = String.valueOf(obj);
		Date dateObj = null;
		try {
			dateObj = new SimpleDateFormat(srcPattern).parse(strObj);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new SimpleDateFormat(dstPattern).format(dateObj);
	}

	
	public static Calendar convertStringToCalendar(String dateString) {
		return convertStringToCalendar(dateString, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static Calendar convertStringToCalendar(String dateString, String srcPattern) {
		Date dateObj = null;
		try {
			dateObj = new SimpleDateFormat(srcPattern).parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateObj);
		return cal;
	}
	
	public static String genRadnomString(int size) {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < size; i++) {
		    int rIndex = rnd.nextInt(3);
		    switch (rIndex) {
		    case 0:
		        // a-z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
		        break;
		    case 1:
		        // A-Z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
		        break;
		    case 2:
		        // 0-9
		        temp.append((rnd.nextInt(10)));
		        break;
		    }
		}
		
		return temp.toString();
	}
	
	public static String cutHangul(String inputString, int maxByte) {
		if(inputString == null) return null;
		
		byte[] inputByte = inputString.getBytes();

		int byteLength = inputByte.length;
		// ????????? 3byte??? ???????????? ????????????
		int stringLength = inputString.length();
		
		int hangulCount = (byteLength - stringLength) / 2;
		
		// ????????? 2byte??? ??????
		int smsByte = hangulCount * 2 + (stringLength - hangulCount);
		if(maxByte <= 0) {
			return null;
		}
		
		if(maxByte >= smsByte) {
			return inputString;
		}
		
		// ... ?????? maxByte ??????
		maxByte -= 3;
		if(maxByte <= 0) {
			return null;
		}
		
		int cutByte = 0;
		int smsCutByte = 0;
		for(int i = 0; i < inputString.length() - 1; i++) {
			if(isIncludeHangul(inputString.substring(i, i + 1))) {
				if(smsCutByte + 2 > maxByte) {
					break;
				}
				smsCutByte += 2;
				cutByte += 3;
			} else {
				if(smsCutByte + 1 > maxByte) {
					break;
				}
				smsCutByte += 1;
				cutByte += 1;
			}
		}

		return new String(inputByte, 0, cutByte) + "...";
	}
	
	public static boolean isIncludeHangul(String input) {
		for(int i = 0; i < input.length(); i++) {
			if(Character.getType(input.charAt(i)) == Character.OTHER_LETTER) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void checkAutoFinishCondi(String mberId, String lecId, String applId) {
		
		String autoFinishCondi = "";
		
		List<Map<String, Object>> condiList = convertJsonStringToMapList(autoFinishCondi);
		
		// ???????????? enabled??? ????????? ?????? ?????? ????????? ????????????.
		boolean isAutoFinishCheck = false;
		boolean isAutoFinish = true;
		for(Map<String, Object> condi : condiList) {
			String enabled = condi.get("enabled") == null ? "" : String.valueOf(condi.get("enabled"));
			if(enabled != null && enabled.equals("true")) {
				isAutoFinishCheck = true;
				String name = condi.get("name") == null ? "" : String.valueOf(condi.get("name"));
				String value = condi.get("value") == null ? "" : String.valueOf(condi.get("value"));
				switch (name) {
				// value <= ????????? %
				case "???????????????":
					
					break;
				// value <= ????????? ?????? ????????? ????????????
				case "????????? ????????????":
					
					break;
				// value <= ????????? %
				case "??????":
					
					break;
				// value <= ?????? ?????? ??????
				case "??????":
					
					break;
				// value ????????? 1????????? ?????? ?????? ???????????? ??????
				case "??????":
				case "??????":
					
					break;
				// value ????????? 1????????? ?????? ?????? ?????? ???????????? ??????
				case "??????":
					
					break;
				}
			}
			
 		}
		
		// ???????????? ????????? ?????? ?????? ?????? ????????? ???????????? ?????? ?????? ????????????
		if(isAutoFinishCheck && isAutoFinish) {
			
		}
	}
}