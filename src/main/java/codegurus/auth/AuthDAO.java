package codegurus.auth;

import codegurus.auth.vo.ReqDupCheckVO;
import codegurus.auth.vo.ReqRegisterVO;
import codegurus.auth.vo.ReqTrialRegisterVO;

public interface AuthDAO {

    int selectUserDup(ReqDupCheckVO reqVo);
    void insertRegisterInfo(ReqRegisterVO reqVo);
    void insertTrialRegister(ReqTrialRegisterVO reqVo);
}