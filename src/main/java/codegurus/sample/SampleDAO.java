package codegurus.sample;

import codegurus.sample.vo.SampleVO;
import codegurus.sample.vo.ReqSampleListVO;
import codegurus.sample.vo.ReqSampleVO;

import java.util.List;

public interface SampleDAO {
	
	List<SampleVO> selectTestList(ReqSampleListVO reqVo);
	void insertTest(ReqSampleVO reqVo);
}