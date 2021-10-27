package codegurus.board.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 클라이언트 버전 VO
 */
@Getter
@Setter
public class ClientVersionVO {

    @ApiModelProperty(notes = "클라이언트 버전 id")
    private String clientVersionId;

    @ApiModelProperty(notes = "클라이언트 이름")
    private String clientName;

    @ApiModelProperty(notes = "클라이언트 구분")
    private String clientDivision;

    @ApiModelProperty(notes = "클라이언트 유형(ex: aos,ios 등)")
    private String clientType;

    @ApiModelProperty(notes = "버전 이름(ex: 1.0.2)")
    private String versionName;

    @ApiModelProperty(notes = "버전코드")
    private String versionCode;

    @ApiModelProperty(notes = "강제 갱신")
    private String forcingUpdateOrnot;

    @ApiModelProperty(notes = "갱신 구분")
    private String updateDivision;

    @ApiModelProperty(notes = "다운로드 url")
    private String downloadUrl;

    @ApiModelProperty(notes = "내용")
    private String content;

    @ApiModelProperty(notes = "활성화 여부")
    private String activateOrnot;

    @ApiModelProperty(notes = "등록 ID")
    private String regId;

    @ApiModelProperty(notes = "등록 날짜")
    private String regDate;
}
