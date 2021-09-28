package codegurus.auth;

import codegurus.auth.vo.*;
import codegurus.cmm.vo.req.ReqAuthVO;

import java.util.List;
import java.util.Map;

public interface AuthDAO {

    int selectUserDup(ReqDupCheckVO reqVo);
    void insertRegisterInfo(ReqRegisterBaseVO reqVo);
    void insertTrialRegister(ReqTrialRegisterVO reqVo);
    void insertUserAuth(ReqRegisterBaseVO params);
    ResTrialUserVO selectTrialUser(String trialManageId);
    void insertSmsCert(ReqSmsCertVO reqVo);
    Map<String, String> selectSmsCert(ReqSmsCertCfmVO reqVo);
    int updateSmsCert(ReqSmsCertCfmVO reqVo);
    String selectUserId(ReqFindIDVO reqVo);
    UserVO selectUserPassword(ReqFindPWVO reqVo);
    String selectConnectedFamilyYN(Map<String, String> qp);
    void insertFamilyRelation(Map<String, String> qp);
    List<ChildInfoVO> selectChildrenByParent(String userManageFamilyId);
    List<ResContractInfoElemVO> selectContractInfo(ReqContractInfoVO reqVo);
    Map<String, Object> callGetOnlineSubjInfo(Map<String, Object> map);
    void insertUserSubject(Map<String, String> map);
    int updateUserAuth(Map<String, Object> map);
    List<String> selectUserSubjectList(String userManageId);
    int updateUserInfoByFullmemberAuth(ReqContractInfoVO reqVo);
    int updateTrialManageId(ReqAuthVO reqVo);
}