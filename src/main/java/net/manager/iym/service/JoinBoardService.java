package net.manager.iym.service;

import net.manager.iym.domain.JoinBoard;
import net.manager.iym.domain.Member;
import net.manager.iym.dto.JoinBoardDTO;

import net.manager.iym.dto.paging.PageRequestDTO;
import net.manager.iym.dto.paging.PageResponseDTO;
import org.hibernate.mapping.Join;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JoinBoardService {

    Long register(JoinBoardDTO joinBoardDTO);//조인보드게시글 등록 메소드 오버라이드

    JoinBoardDTO read(Long joinBoardNum);//조인보드게시글 확인 메소드 오버라이드

    void modify(JoinBoardDTO joinBoardDto);//조인보드게시글 수정 메소드 오버라이드

    void remove(Long joinBoardNum);//조인보드게시글 삭제 메소드 오버라이드

    PageResponseDTO<JoinBoardDTO> list(PageRequestDTO pageRequestDTO);

    void updateJoinBoardNum(Long joinBoardNum);//조회수 증가시킴

    default JoinBoard dtoToEntity(JoinBoardDTO joinBoardDTO){//defalut를 써서 implement한 클래스도 사용가능
        //dto를 Entity로 매핑해주는 작업을 함
        JoinBoard joinBoard = JoinBoard.builder()
                .joinBoardNum(joinBoardDTO.getJoinBoardNum())
                .joinTitle(joinBoardDTO.getJoinTitle())
                .joinContent(joinBoardDTO.getJoinContent())
                .joinVisitCount(joinBoardDTO.getJoinVisitCount())
                .joinType(joinBoardDTO.getJoinType()).build();
        return  joinBoard;//joinBoardDTO에서는 id값만 있고 Member값이 없기 때문에 Member값을 따로 추가해주어야한다.
    }
    default JoinBoardDTO EntityToDto(JoinBoard joinBoard){
        JoinBoardDTO joinBoardDTO = JoinBoardDTO.builder()
                .joinBoardNum(joinBoard.getJoinBoardNum())
                .joinTitle(joinBoard.getJoinTitle())
                .joinContent(joinBoard.getJoinContent())
                .joinVisitCount(joinBoard.getJoinVisitCount())
                .joinType(joinBoard.getJoinType())
                .id(joinBoard.getMember().getId())
                .regDate(joinBoard.getRegDate())
                .modDate(joinBoard.getModDate()).build();//이름
        return joinBoardDTO;
    }
}
