package egovframework.com.cmm.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 인증여부 체크 인터셉터
 * @author 공통서비스 개발팀 서준식
 * @since 2011.07.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011.07.01  서준식          최초 생성
 *  2011.09.07  서준식          인증이 필요없는 URL을 패스하는 로직 추가
 *  2017.08.31  장동한          인증된 사용자 체크로직 변경 및 관리자 권한 체크 로직 추가 
 *  </pre>
 */


public class AuthenticInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private Environment environment;
	
	/** log */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticInterceptor.class);
	
	/** 관리자 접근 권한 패턴 목록 */
	private List<String> adminAuthPatternList;
	
	public List<String> getAdminAuthPatternList() {
		return adminAuthPatternList;
	}

	public void setAdminAuthPatternList(List<String> adminAuthPatternList) {
		this.adminAuthPatternList = adminAuthPatternList;
	}

	/**
	 * 인증된 사용자 여부로 인증 여부를 체크한다.
	 * 관리자 권한에 따라 접근 페이지 권한을 체크한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		/*//인증된사용자 여부
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	
		//미민증사용자 체크
		if(!isAuthenticated) {
			ModelAndView modelAndView = new ModelAndView("redirect:/uat/uia/egovLoginUsr.do");
			throw new ModelAndViewDefiningException(modelAndView);
		}
		//인증된 권한 목록
		List<String> authList = (List<String>)EgovUserDetailsHelper.getAuthorities();
		//관리자인증여부
		boolean adminAuthUrlPatternMatcher = false;
		//AntPathRequestMatcher
		AntPathRequestMatcher antPathRequestMatcher = null;
		//관리자가 아닐때 체크함
		for(String adminAuthPattern : adminAuthPatternList){
			antPathRequestMatcher = new AntPathRequestMatcher(adminAuthPattern);
			if(antPathRequestMatcher.matches(request)){
				adminAuthUrlPatternMatcher = true;
			}
		}
		//관리자 권한 체크
		if(adminAuthUrlPatternMatcher && !authList.contains("ADMIN")){
			ModelAndView modelAndView = new ModelAndView("redirect:/uat/uia/egovLoginUsr.do?auth_error=1");
			throw new ModelAndViewDefiningException(modelAndView);
		}
		return true;*/

		//==================================== 임시 주석처리 (20210604 이프로) - start ====================================
//		//header에 accessToken  존재 여부 확인
//		String authorization = request.getHeader("Authorization");
//
//		if(authorization == null) {
//			String json = "authorization empty in header";
//			sendJson(response, json);
//			return false;
//		}
//
//		String headerToken = request.getHeader("Authorization").split(" ")[1];
//
//		if(headerToken == null) {
//			String json = "accessToken empty in header";
//			sendJson(response, json);
//			return false;
//		}
//
//		//accessToken 존재여부 확인
//		TokenVO vo = new TokenVO();
//		vo.setToken(headerToken);
//		TokenVO tokenVO = loginDAO.selectOneToken(vo);
//
//		if(tokenVO == null) {
//			String json = "no accessToken data in server";
//			sendJson(response, json);
//			return false;
//		}
//
//		//SimpleDateFormat 사용
//		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		//expire date 확인
//		Calendar cal1 = Calendar.getInstance();
//		cal1.setTime(sdformat.parse(tokenVO.getExpire_date()));
//
//		Calendar cal2 = Calendar.getInstance();
//		cal2.setTime(new Date());
//
//		if(cal1.compareTo(cal2) < 0) {
//			String json = "The expiration time has passed";
//			sendJson(response, json);
//			return false;
//		}
		//==================================== 임시 주석처리 (20210604 이프로) - end ======================================
		
		return true;
	}

	private void sendJson(HttpServletResponse response, String json) throws IOException {
		
		response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        
        PrintWriter out = response.getWriter();
        out.write(json);
        
        out.flush();
        out.close();

	}
}
