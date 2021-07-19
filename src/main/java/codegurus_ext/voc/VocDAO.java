package codegurus_ext.voc;

import codegurus.cmm.vo.req.ReqDupCheckVO;

public interface VocDAO {

    int selectUserDup(ReqDupCheckVO reqVo);
}