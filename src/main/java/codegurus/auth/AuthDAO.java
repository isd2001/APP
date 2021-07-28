package codegurus.auth;

import codegurus.auth.vo.*;

import java.util.Map;

public interface AuthDAO {

    int selectUserDup(ReqDupCheckVO reqVo);
    void insertRegisterInfo(ReqRegisterVO reqVo);
    void insertTrialRegister(ReqTrialRegisterVO reqVo);
    void insertUserAuth(Map<String, Object> params);
    ResTrialUserVO selectTrialUser(String trialManageId);
    void insertSmsCert(ReqSmsCertVO reqVo);
    Map<String, String> selectSmsCert(ReqSmsCertCfmVO reqVo);
    int updateSmsCert(ReqSmsCertCfmVO reqVo);
}