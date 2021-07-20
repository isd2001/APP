package codegurus.auth;

import codegurus.auth.vo.ReqDupCheckVO;
import codegurus.auth.vo.ReqRegisterVO;

public interface AuthDAO {

    int selectUserDup(ReqDupCheckVO reqVo);
    void insertRegisterInfo(ReqRegisterVO reqVo);
}