package net.manager.iym.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamBoardDTO {

    private Long teamBoardNum;

    private String id;

    private String teamBoardTitle;

    private String teamBoardContent;

    private Long teamBoardVisitCount;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    //private Long teamNum;



}
