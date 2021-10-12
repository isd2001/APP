package codegurus.cmm.controller;

import codegurus.cmm.service.FileService;
import codegurus.cmm.vo.res.Res;
import codegurus.cmm.vo.res.ResFileVO;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovBasicLogger;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class CmmController extends BaseController {

	@Autowired
//	private DefaultBeanValidator beanValidator;
	private static final Logger LOGGER = LoggerFactory.getLogger(CmmController.class);
	
	@Autowired
	private FileService fileService;

	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	/**
	 * 전자정부 파일 업로드 모듈
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/file")
	public ResponseEntity<String> file(MultipartHttpServletRequest request) throws Exception {

		String result = "";
		try {
			result = fileService.uploadFiles(request.getFileMap());
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 전자정부 파일 업로드 모듈
	 *
	 * 	- file()을 기반으로, 한솔 프로젝트 하면서 공통 json response 형식으로 변경한 것
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 이프로
	 * @version 2021.06
	 */
	@RequestMapping("/uploadFile")
	public Res<ResFileVO> uploadFile(MultipartHttpServletRequest request) throws Exception {

		return new Res<ResFileVO>(new ResFileVO(fileService.uploadFiles(request.getFileMap())));
	}
	
	@RequestMapping(value = "/download/{id}")
	public void download(@PathVariable("id") final String id, @RequestParam Map<String, Object> commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.debug("## 파일 다운로드 id:[{}]", id);

		long fileSn = commandMap.get("fileSn") == null ? 0 : Long.parseLong((String) commandMap.get("fileSn"));
		String disposition = (String) commandMap.get("disposition");

		if(disposition == null || disposition.equals("")) {
			disposition = "attachment;";
		}

		FileVO fileVO = new FileVO();
		fileVO.setAtchFileId(id);
//		fileVO.setFileSn(fileSn);
		fileVO.setFileSn(String.valueOf(fileSn)); // 컴파일 오류 수정 (20210603 이프로)
		FileVO fvo = fileService.downloadFile(fileVO);

		if (fvo == null) {
			log.debug("## 다운로드할 파일이 존재하지 않습니다.");
			return;
		}

		File uFile = new File(fvo.getFileStreCours(), fvo.getStreFileNm());
		long fSize = uFile.length();

		if (fSize > 0) {
			String mimetype = "application/x-msdownload";
			
			String userAgent = request.getHeader("User-Agent");
			HashMap<String,String> result = EgovBrowserUtil.getBrowser(userAgent);
			if ( !EgovBrowserUtil.MSIE.equals(result.get(EgovBrowserUtil.TYPEKEY)) ) {
				mimetype = "application/x-stuff";
			}
			
			if(fvo.getFileExtsn().toLowerCase().equals("pdf")) {
				mimetype = "application/pdf";
			}
			if(fvo.getFileExtsn().toLowerCase().equals("jpg")
					|| fvo.getFileExtsn().toLowerCase().equals("png")
					|| fvo.getFileExtsn().toLowerCase().equals("gif") ) {
				mimetype = "image/" + fvo.getFileExtsn().toLowerCase();
			}

			try {
				String contentDisposition = EgovBrowserUtil.getDisposition(disposition, fvo.getOrignlFileNm(),userAgent,"UTF-8");
				response.setHeader("Content-Disposition", contentDisposition);
			} catch(Exception e) {
				LOGGER.error("user agent type : " + userAgent);
				LOGGER.error("result : " + result.toString());
				LOGGER.error("download error1 : " + e.toString());
			}
			
			//response.setBufferSize(fSize);	// OutOfMemeory 발생
			response.setContentType(mimetype);
			//response.setHeader("Content-Disposition", "attachment; filename=\"" + contentDisposition + "\"");
			
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("access-control-allow-origin", "*");
			
			response.setContentLengthLong(fSize);

			/*
			 * FileCopyUtils.copy(in, response.getOutputStream());
			 * in.close();
			 * response.getOutputStream().flush();
			 * response.getOutputStream().close();
			 */
			BufferedInputStream in = null;
			BufferedOutputStream out = null;

			try {
				in = new BufferedInputStream(new FileInputStream(uFile));
				out = new BufferedOutputStream(response.getOutputStream());

				FileCopyUtils.copy(in, out);
				out.flush();
			} catch (IOException ex) {
				// 다음 Exception 무시 처리
				// Connection reset by peer: socket write error
				EgovBasicLogger.ignore("IO Exception", ex);
			} finally {
				EgovResourceCloseHelper.close(in, out);
			}

		} else {
			response.setContentType("application/x-msdownload");

			PrintWriter printwriter = response.getWriter();
			
			printwriter.println("<html>");
			printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fvo.getOrignlFileNm() + "</h2>");
			printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			printwriter.println("<br><br><br>&copy; webAccess");
			printwriter.println("</html>");
			
			printwriter.flush();
			printwriter.close();
		}
	}
	
	
	@RequestMapping(value="/code", method=RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> code(@RequestBody ComDefaultCodeVO vo, BindingResult bindingResult) throws Exception {
			
		Map<String,Object> result = new HashMap<>();
		
//		beanValidator.validate(vo, bindingResult);
//        if (bindingResult.hasErrors()) {
//        	bindingResult.getFieldErrors().stream().forEach(f -> result.put("msg", (f.getField() + ": " + f.getDefaultMessage())));
//            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
//        }
        
		try {
			result.put("list", cmmUseService.selectCmmCodeDetail(vo));
			
			return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(result, HttpStatus.BAD_REQUEST);
		}
	}
}