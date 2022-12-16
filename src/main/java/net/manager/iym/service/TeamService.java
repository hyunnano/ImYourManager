package net.manager.iym.service;

import net.manager.iym.domain.Member;
import net.manager.iym.dto.MemberDTO;
import net.manager.iym.dto.MemberListDTO;
import net.manager.iym.dto.TeamDTO;
import net.manager.iym.dto.paging.PageRequestDTO;
import net.manager.iym.dto.paging.PageResponseDTO;

import java.util.List;

public interface TeamService {

    Long register(TeamDTO teamDTO);//팀생성 메소드
    TeamDTO readOne(Long teamNum);//팀정보 확인 메소드
    void modify(TeamDTO teamDTO);//팀 수정 메소드
    void remove(Long teamNum);// 팀 제거 메소드
    void teamJoin(TeamDTO teamDTO);//팀 가입 메소드
    PageResponseDTO<TeamDTO> list(PageRequestDTO pageRequestDTO);//팀리스트/페이징 메소드
    List<MemberListDTO> teamMemberlist(Long teamNum);//팀원 리스트 메소드
    void removeTeamMember(String id); //팀원 추방 메소드
}
