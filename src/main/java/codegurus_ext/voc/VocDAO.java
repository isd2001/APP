package codegurus_ext.voc;

import codegurus.auth.vo.ReqDupCheckVO;

public interface VocDAO {

    int selectUserDup(ReqDupCheckVO reqVo);
}