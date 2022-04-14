package codegurus.learning.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 학습 콘텐츠 이력 저장 요청 VO
 */
@Getter
@Setter
public class ReqLearningContentsHistoryUpdateVO extends ReqBaseVO {

    @ApiModelProperty(notes = "콘텐츠 이력 ID",  required = true)
    private String contentsHistoryId;

    @ApiModelProperty(notes = "토론 주제 사진 파일 ID 1", required = false, example = "FILE_000000000000011")
    private String debateTopicPhotoFileId1;
    @ApiModelProperty(notes = "토론 주제 사진 파일 ID 2", required = false, example = "FILE_000000000000011")
    private String debateTopicPhotoFileId2;
    @ApiModelProperty(notes = "토론 주제 사진 파일 ID 3", required = false, example = "FILE_000000000000011")
    private String debateTopicPhotoFileId3;
    @ApiModelProperty(notes = "토론 주제 사진 파일 ID 4", required = false, example = "FILE_000000000000011")
    private String debateTopicPhotoFileId4;

    @ApiModelProperty(notes = "쓰기 주제 사진 파일 ID 1", required = false, example = "FILE_000000000000011")
    private String writingTopicPhotoFileId1;
    @ApiModelProperty(notes = "쓰기 주제 사진 파일 ID 2", required = false, example = "FILE_000000000000011")
    private String writingTopicPhotoFileId2;
    @ApiModelProperty(notes = "쓰기 주제 사진 파일 ID 3", required = false, example = "FILE_000000000000011")
    private String writingTopicPhotoFileId3;
    @ApiModelProperty(notes = "쓰기 주제 사진 파일 ID 4", required = false, example = "FILE_000000000000011")
    private String writingTopicPhotoFileId4;

    @ApiModelProperty(hidden = true)
    private String userManageId;
}
