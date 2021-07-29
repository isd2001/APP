package codegurus.cmm.aop;

import codegurus.auth.AuthDAO;
import codegurus.auth.vo.ResTrialUserVO;
import codegurus.cmm.constants.ResCodeEnum;
import codegurus.cmm.exception.CustomException;
import codegurus.cmm.jwt.JwtFilter;
import codegurus.cmm.jwt.TokenProvider;
import codegurus.cmm.util.ContextUtil;
import codegurus.cmm.util.JsonUtil;
import codegurus.cmm.util.StringUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 권한 검증 인터셉터
 *
 *  - TODO: 전반적으로 캐시 처리가 가능한지 검토해 보자.
 *
 * @author 이프로
 * @version 2021.07
 */
@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter {

    // 시점문제인지 어차피 autowire가 안된다.
//    @Autowired
//    private TokenProvider tokenProvider;
//    @Autowired
//    private AuthDAO authDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("## AuthInterceptor.preHandle() called!");

        // 토큰 정보에서 사용자 정보 획득
//        String jwt = StringUtil.trim(request.getHeader("Authorization")).substring(JwtFilter.BEARER_PREFIX.length() + 1);
        String jwt = StringUtil.trim(JwtFilter.resolveToken(request));
        log.debug("## jwt:[{}]", jwt);

        Claims claims = ContextUtil.getBean(TokenProvider.class).parseClaims(jwt);
        log.debug("## claims: {}", JsonUtil.toJson(claims));

        // 체험회원일 경우
        if (TokenProvider.TRIAL_USER.equals(claims.get(TokenProvider.SUBJECT_KEY))) {
            log.debug("## 체험회원일 경우");

            String trialManageId = StringUtil.trim(claims.get("trialManageId"));
            log.debug("## trialManageId:[{}]", trialManageId);

            ResTrialUserVO trialUser = ContextUtil.getBean(AuthDAO.class).selectTrialUser(trialManageId);
            log.debug("## trialUser:[{}]", JsonUtil.toJson(trialUser));

            // 유효하지 않은 회원 (삭제되었거나 정상상태가 아니거나, 없는 trialManageId)
            if(trialUser == null){
                throw new CustomException(ResCodeEnum.INFO_0009);
            }

            // 체험 기간 만료
            if("N".equals(trialUser.getValidPeriod())){
                throw new CustomException(ResCodeEnum.INFO_0003);
            }


        // 체험회원이 아닐 경우
        }else{
            log.debug("## 체험회원이 아닐 경우");

        }


        return true;
    }

}
