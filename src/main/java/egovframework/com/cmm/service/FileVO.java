package egovframework.com.cmm.service;

import java.io.Serializable;
import java.util.List;

import codegurus.cmm.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author 공통 서비스 개발팀 이삼섭
 * @Class Name : FileVO.java
 * @Description : 파일정보 처리를 위한 VO 클래스
 * @Modification Information
 * <p>
 * 수정일       수정자         수정내용
 * -------        -------     -------------------
 * 2009. 3. 25.     이삼섭
 * @see
 * @since 2009. 3. 25.
 */
@Getter
@Setter
public class FileVO implements Serializable {

    @ApiModelProperty(notes = "첨부파일 아이디")
    public String atchFileId = "";

    @ApiModelProperty(notes = "생성일자")
    public String creatDt = "";

    @ApiModelProperty(notes = "파일내용")
    public String fileCn = "";

    @ApiModelProperty(notes = "파일확장자")
    public String fileExtsn = "";

    @ApiModelProperty(notes = "파일크기")
    public long fileMg = 0;

    @ApiModelProperty(notes = "파일연번")
    public String fileSn = "";

    @ApiModelProperty(notes = "파일저장경로")
    public String fileStreCours = "";

    @ApiModelProperty(notes = "원파일명")
    public String orignlFileNm = "";

    @ApiModelProperty(notes = "저장파일명")
    public String streFileNm = "";

    @ApiModelProperty(notes = "다운로드 횟수")
    public int downloadCnt = 0;


    @Override
    public String toString() {
        return StringUtil.genToString(this);
    }

}
