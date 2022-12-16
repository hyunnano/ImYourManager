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
public class NoticeBoardDTO {

    private Long noticeBoardNum;
    //@NotEmpty
    @Size(min = 1, max = 100)
    private String noticeTitle;
    //@NotEmpty
    private String noticeContent;
    //@NotEmpty
    private String id;
    //@NotEmpty
    private Long noticeVisitCount;
    //@NotEmpty
    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
