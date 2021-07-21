package codegurus.auth;

import codegurus.auth.vo.ReqDupCheckVO;
import codegurus.auth.vo.ReqRegisterVO;
import codegurus.auth.vo.ReqTrialRegisterVO;
import codegurus.auth.vo.ResTrialUserVO;

public interface AuthDAO {

    int selectUserDup(ReqDupCheckVO reqVo);
    void insertRegisterInfo(ReqRegisterVO reqVo);
    void insertTrialRegister(ReqTrialRegisterVO reqVo);
    ResTrialUserVO selectTrialUser(String trialManageId);
}