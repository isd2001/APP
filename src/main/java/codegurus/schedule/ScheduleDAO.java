package codegurus.schedule;

import codegurus.schedule.vo.*;

import java.util.List;

/**
 * 도서일정 DAO
 *
 * @author 이미란
 * @version 2021.07
 */
public interface ScheduleDAO {

    List<ResSubjectListElemVO> selectSubjectList(ReqSubjectListVO reqVo);
    List<ResScheduleVO> selectBookScheduleList(ReqScheduleListVO reqVo);
    List<ResScheduleVO> selectThisMonthBookList(ReqScheduleListVO reqVo);

    ResScheduleVO selectPopup(ReqSchedulePopupVO reqVo);

}
