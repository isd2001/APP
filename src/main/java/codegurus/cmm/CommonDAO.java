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
	 * @param userId 사용자ID(로그인ID)임
	 * @return
	 */
	UserVO selectUserByUserId(String userId);

	/**
	 * 사용자ID로 사용자정보 획득
	 *
	 * @param userManageId 사용자관리ID(숫자 PK)임
	 * @return
	 */
	UserVO selectUserByUserManageId(String userManageId);

	/**
	 *
	 * @param params
	 * @return
	 */
	List<FileVO> selectFileListByFileId(Map<String, Object> params);
}