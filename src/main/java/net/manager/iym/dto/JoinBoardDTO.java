package net.manager.iym.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.manager.iym.domain.JoinBoard;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinBoardDTO {//Entity에서 필요한 필드값들을 받아와 get,set을 통해 출력을 해주거나 받은 DTO 값들을 Entity로 보내준다.

    //    @NotEmpty
    private Long joinBoardNum; //NotEmpty를 사용하면 변환시 null값이 넘어와 오류가 생김
    //@NotEmpty
    @Size(min = 1, max = 100)
    private String joinTitle;
    //@NotEmpty
    private String joinContent;
    //@NotEmpty
    private String id;
    //@NotEmpty
    private String joinType;
    //@NotEmpty
    private Long joinVisitCount;
    //@NotEmpty
    private LocalDateTime regDate;

    private LocalDateTime modDate;


}

