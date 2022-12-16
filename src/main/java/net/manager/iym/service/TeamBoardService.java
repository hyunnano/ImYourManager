package net.manager.iym.service;

import net.manager.iym.domain.TeamBoard;
import net.manager.iym.dto.TeamBoardDTO;
import net.manager.iym.dto.paging.PageRequestDTO;
import net.manager.iym.dto.paging.PageResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface TeamBoardService {

    Long register(TeamBoardDTO teamBoardDTO);

    TeamBoardDTO readOne(Long joinBoardNum);

    void modify(TeamBoardDTO teamBoardDTO);

    void remove(Long teamBoardNum);

    PageResponseDTO<TeamBoardDTO> list(PageRequestDTO pageRequestDTO);

    void updateTeamBoardNum(Long teamBoardNum);

    default TeamBoard dtoToEntity(TeamBoardDTO teamBoardDTO){

        TeamBoard teamBoard = TeamBoard.builder()
                .teamBoardNum(teamBoardDTO.getTeamBoardNum())
                .teamBoardTitle(teamBoardDTO.getTeamBoardTitle())
                .teamBoardContent(teamBoardDTO.getTeamBoardContent())
                .teamBoardVisitCount(teamBoardDTO.getTeamBoardVisitCount())
                .build();


        return teamBoard;
    }

    default TeamBoardDTO entityToDTO(TeamBoard teamBoard) {

        TeamBoardDTO teamBoardDTO = TeamBoardDTO.builder()
                .teamBoardNum(teamBoard.getTeamBoardNum())
                .teamBoardTitle(teamBoard.getTeamBoardTitle())
                .teamBoardContent(teamBoard.getTeamBoardContent())
                .teamBoardVisitCount(teamBoard.getTeamBoardVisitCount())
                .id(teamBoard.getMember().getId())
                .regDate(teamBoard.getRegDate())
                .modDate(teamBoard.getModDate())
                .build();
        return teamBoardDTO;
    }
}
