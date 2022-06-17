package codegurus.auth;

import codegurus.auth.vo.*;
import codegurus.cmm.vo.req.ReqAuthVO;

import java.util.List;
import java.util.Map;

public interface AuthDAO {

    int selectUserDup(ReqDupCheckVO reqVo);
    void insertRegisterInfo(ReqRegisterBaseVO reqVo);
    void insertRegisterParentInfo(ReqRegisterBaseVO reqVo);
    void insertTrialRegister(ReqTrialRegisterVO reqVo);
    void insertUserAuth(ReqRegisterBaseVO params);
    int selectUserAuthCount(Map<String, String> map);
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
    int selectUserSubjectCount(Map<String, String> map);
    int updateUserAuth(Map<String, Object> map);
    List<String> selectUserSubjectList(String userManageId);
    int updateUserInfoByFullmemberAuth(ReqContractInfoVO reqVo);
    int updateTrialManageId(ReqAuthVO reqVo);

    int deleteAppPushToken(ReqAuthVO reqVo);
    int insertAppPushToken(ReqAuthVO reqVo);

    int insertScheduleInterval(ScheduleIntervalVO reqVo);
    int selectScheduleInterval(ScheduleIntervalVO reqVo);
    int deleteScheduleInterval(ScheduleIntervalVO reqVo);

    List<ScheduleInfoVO> selectScheduleInfo(ScheduleInfoVO reqVo);

    List<Map<String, Object>> selectOnlineSubjectSapMappingList(Map<String, String> map);
    void insertEduCntrCp(Map<String, String> map);

    int updateUserSubjectActive(ResContractInfoElemVO reqVo);

    int deleteServerData(ReqServerDataSaveVO reqVo);
    void insertServerData(ReqServerDataSaveVO reqVo);
    ServerDataVO selectServerData(ReqServerDataVO reqVo);
}