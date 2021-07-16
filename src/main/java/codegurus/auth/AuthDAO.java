package codegurus.auth;

import codegurus.cmm.vo.req.ReqDupCheckVO;
import codegurus.cmm.vo.req.ReqRegisterVO;

public interface AuthDAO {

    int selectUserDup(ReqDupCheckVO reqVo);
    void insertRegisterInfo(ReqRegisterVO reqVo);
}