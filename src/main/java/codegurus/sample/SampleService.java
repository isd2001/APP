package codegurus.sample;

import codegurus.sample.vo.SampleVO;
import codegurus.sample.vo.ReqSampleListVO;
import codegurus.sample.vo.ReqSampleVO;
import codegurus.sample.vo.ResSampleListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 서비스 클래스 샘플
 *
 * @author 이프로
 * @version 2021.06
 */
@Slf4j
@Service
public class SampleService {

	@Autowired
	private SampleDAO sampleDAO;

	/**
	 * 샘플 목록조회
	 *
	 * @param reqVo
	 * @return
	 */
	public ResSampleListVO selectTestList(ReqSampleListVO reqVo) {

		ResSampleListVO resVo = new ResSampleListVO();

		List<SampleVO> list = sampleDAO.selectTestList(reqVo);
		resVo.setList(list);

		return resVo;
	}

	public void insertTest(ReqSampleVO reqVo){

		ResSampleListVO resVo = new ResSampleListVO();

		sampleDAO.insertTest(reqVo);

		// 트랜잭션 테스트
//		if(true) throw new BaseException("@@");
//		sampleDAO.insertTest(reqVo);
	}

}

