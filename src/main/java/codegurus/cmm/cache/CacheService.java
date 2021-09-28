package codegurus.cmm.cache;

import codegurus.auth.AuthDAO;
import codegurus.auth.vo.ResTrialUserVO;
import codegurus.auth.vo.UserVO;
import codegurus.cmm.CommonDAO;
import codegurus.cmm.jwt.JwtFilter;
import codegurus.cmm.jwt.TokenProvider;
import codegurus.cmm.util.StringUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 사용자정보, 학습정보 등을 제공하는 세션과 같은 역할을 하는 서비스
 *
 * 	- redis가 구축되지 않은 상황에서는 일단 db select 로 처리한다.
 *
 * @author 이프로
 * @version 2021.06
 */
@Slf4j
@Service
public class CacheService {

	@Autowired
	private HttpServletRequest httpRequest;

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private AuthDAO authDAO;

	// TODO: redis autowiring

	/**
	 * spring security principal 획득
	 *
	 * @return
	 */
	public Object getPrincipal(){

		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	/**
	 * 현재 token(request scope)의 사용자정보 조회
	 *
	 * @return
	 */
	public UserVO getTokenUser(){
		String jwt = StringUtil.trim(JwtFilter.resolveToken(httpRequest));

		Claims claims = tokenProvider.parseClaims(jwt);

		String username = StringUtil.trim(claims.get(TokenProvider.SUBJECT_KEY));

		if (TokenProvider.TRIAL_USER.equals(username)) {
			log.debug("## 체험회원일 경우");
			String trialManageId = StringUtil.trim(claims.get("trialManageId"));

			UserVO userVo = new UserVO();
			userVo.setUserManageId("-1");
			ResTrialUserVO trialUser = authDAO.selectTrialUser(trialManageId);
			userVo.setUsername(username);
			userVo.setName(trialUser.getName());
			userVo.setBirth(trialUser.getBirth());
			return userVo;
		} else {
			log.debug("## 회원일 경우");

			return commonDAO.selectUserByUserId(username);
		}

//		Object principal = getPrincipal();
//		log.debug("## principal:[{}]", principal);
//		String username = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : principal.toString();
//		log.debug("## JWT username:[{}]", username);

//		return commonDAO.selectUserByUserId(username);
	}

	/**
	 * 현재 token(request scope)의 사용자관리ID 조회
	 *
	 * @return
	 */
	public String getUserManageId(){

		return Optional.ofNullable(getTokenUser()).map(o -> o.getUserManageId()).orElse("");
	}

}

