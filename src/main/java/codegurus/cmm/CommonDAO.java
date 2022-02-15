package codegurus.cmm;

import codegurus.auth.vo.UserVO;
import egovframework.com.cmm.service.FileVO;

import java.util.List;
import java.util.Map;

/**
 * 공통 DAO
 *
 * @author 이프로
 * @version 2021.06
 */
public interface CommonDAO {

	/**
	 * 사용자ID로 사용자정보 획득
	 *
	 * @param params
	 * @return
	 */
	UserVO selectUserByUserId(Map<String, String> params);

	/**
	 * 사용자ID로 사용자정보 획득
	 *
	 * @param params
	 * @return
	 */
	UserVO selectUserByUserManageId(Map<String, String> params);

	/**
	 *
	 * @param params
	 * @return
	 */
	List<FileVO> selectFileListByFileId(Map<String, Object> params);
}