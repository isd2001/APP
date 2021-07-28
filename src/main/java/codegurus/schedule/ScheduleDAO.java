package codegurus.schedule;

import codegurus.schedule.vo.ReqScheduleListVO;
import codegurus.schedule.vo.ReqSubjectListVO;
import codegurus.schedule.vo.ResScheduleListElemVO;
import codegurus.schedule.vo.ResSubjectListElemVO;

import java.util.List;

/**
 * 도서일정 DAO
 *
 * @author 이미란
 * @version 2021.07
 */
public interface ScheduleDAO {

    List<ResSubjectListElemVO> selectSubjectList(ReqSubjectListVO reqVo);
    List<ResScheduleListElemVO> selectScheduleList(ReqScheduleListVO reqVo);
}
