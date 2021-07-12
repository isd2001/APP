package codegurus.cmm.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import codegurus.cmm.util.JsonUtil;
import codegurus.cmm.util.StringUtil;
import egovframework.com.cmm.service.impl.FileManageDAO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;


@Slf4j
@Service
public class FileService {
	
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;

	@Autowired
	private FileManageDAO fileManageDAO;

	public String uploadFiles(final Map<String, MultipartFile> files) throws Exception {
		
		if (!files.isEmpty()) {
			 List<FileVO> result = fileUtil.parseFileInf(files, "FILE_", 0, "", "Globals.fileStorePath");
			 return fileMngService.insertFileInfs(result);
	    }
		
		return null;
	}
	
	public FileVO downloadFile(final FileVO fileVO) throws Exception {

		// 다운로드 카운트 증가시키기
		fileMngService.updateDownloadCount(fileVO);
		
		return fileMngService.selectFileInf(fileVO);
	}

	/**
	 * atchFileId 로 FileVO 조회
	 *
	 * 	- atchFileId 로 여러 파일이 조회되어도 첫 번째 한 개만 리턴한다.
	 *
	 * @param fileId
	 * @return
	 */
	public FileVO selectFileVo(String fileId){

		if (StringUtil.isBlank(fileId)){
			return null;
		}
		List<FileVO> fileList = fileManageDAO.selectFileInfs(fileId);
		log.debug("## fileList:[{}]", JsonUtil.toJson(fileList));
		if(fileList.size() > 0){
			return fileList.get(0); // 첫 번째 레코드만 사용
		}
		return null;
	}

}
