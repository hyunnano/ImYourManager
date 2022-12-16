package net.manager.iym.service;

import lombok.RequiredArgsConstructor;
import net.manager.iym.domain.JoinBoard;
import net.manager.iym.domain.Member;
import net.manager.iym.domain.MemberGrade;
import net.manager.iym.domain.Team;
import net.manager.iym.dto.JoinBoardDTO;
import net.manager.iym.dto.MemberDTO;
import net.manager.iym.dto.MemberListDTO;
import net.manager.iym.dto.TeamDTO;
import net.manager.iym.dto.paging.PageRequestDTO;
import net.manager.iym.dto.paging.PageResponseDTO;
import net.manager.iym.repository.MemberRepository;
import net.manager.iym.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {

    private final ModelMapper modelMapper;

    private final TeamRepository teamRepository;

    private final MemberRepository memberRepository;

    @Override
    public Long register(TeamDTO teamDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = ((UserDetails) principal).getUsername();//위에 있는 행과 함께 로그인한 사람의 id를 뽑아옴
        teamDTO.setId(id);//팀DTO에 null값인 id에 로그인한 사람의 아이디를 넣어준다.
        Team team = modelMapper.map(teamDTO, Team.class);//받은 팀 정보를 domain으로 매핑한다.
        teamRepository.save(team);//매핑한 팀을 등록해준다.
        Member member = memberRepository.findMemberById(id);//뽑아온 아이디로 멤버값 뽑음
        member.changeTeam(team);//팀 체인지 메소드를 통해서 로그인한 멤버의 null값이었던 팀값을 방금 생성한 팀의 teamNum으로 변경시킴
        member.addGrade(MemberGrade.TEAMLEADER);//팀을 생성했기에 로그인한 사람의 등급을 리더로 지명
        member.addGrade(MemberGrade.TEAMMEMBER);//팀 리더이면서 팀멤버도 맞기에 멤버도 등록
        memberRepository.save(member);//멤버를 업데이트하여 데이터 베이스에 저장해줌
        return member.getTeam().getTeamNum();//팀 넘버를 리턴해서 팀넘버 알려줌
    }

    @Override
    public TeamDTO readOne(Long teamNum) {
        Team team = teamRepository.findTeamByTeamNum(teamNum);
        TeamDTO teamDTO = modelMapper.map(team, TeamDTO.class);
        return teamDTO;
    }

    @Override
    public void modify(TeamDTO teamDTO) {
        Team team = teamRepository.findTeamByTeamNum(teamDTO.getTeamNum());
        team.changeTeam(teamDTO.getTeamName(),teamDTO.getTeamInfo(), teamDTO.getTeamAge(),
                teamDTO.getTeamType(), teamDTO.getTeamLevel(), teamDTO.getTeamLocal1(), teamDTO.getTeamLocal2());
        teamRepository.save(team);//팀 정보 수정
    }

    @Override
    public void remove(Long teamNum) {//팀삭제
        Team team = teamRepository.findTeamByTeamNum(teamNum);//팀num을 받아와 팀을 검색
        List<Member> memberList = memberRepository.findMembersByTeam(team);//등급을 낮추기 위해 현재 팀원들을 수집
        for (Member member : memberList){//팀 멤버의 등급을 낮추는 for문
            member.removeGrade(MemberGrade.TEAMMEMBER);
            member.removeGrade(MemberGrade.TEAMLEADER);
            memberRepository.save(member);//?????이 과정을 crud로 해결할 수 있다면 어떻게 query문을 짜야 하는지?
        }
        memberRepository.updateDeleteTeam(team);//해당팀이 소속된 멤버에서 팀을 제거해주는 업데이트를 실행
       teamRepository.deleteByTeamNum(teamNum);//팀원이 다 제거되었기 때문에 팀을 삭제하는 Delete문 실행 가능
    }
    @Override
    public void removeTeamMember(String id) {//팀원 강퇴
        Member member = memberRepository.findMemberById(id);
        member.changeTeam(null);
        member.removeGrade(MemberGrade.TEAMMEMBER);
        memberRepository.save(member);
    }
    @Override
    public void teamJoin(TeamDTO teamDTO) {//team에 로그인한 사람을 가입시킨다.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = ((UserDetails) principal).getUsername();// Object타입의 principal객체를 생성하여 로그인한 사람의 id를 뽑아옴
        Team team = modelMapper.map(teamDTO, Team.class); //Team을 멤버에 넣어주기 위해서 TeamDTO를 매핑시켜준다.
        Member member = memberRepository.findMemberById(id); //로그인한 아이디로 멤버 값을 가져온다.
        member.changeTeam(team);//멤버에 팀을 셋팅해준다.
        member.addGrade(MemberGrade.TEAMMEMBER);//등급에 team에 추가해준다.
        memberRepository.save(member);//수정한 멤버값을 DB에 저장한다.
    }

    @Override
    public PageResponseDTO<TeamDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("teamNum");

        Page<Team> result = teamRepository.searchAll(types, keyword, pageable);

        List<TeamDTO> dtoList = result.getContent().stream()
                .map(team -> modelMapper.map(team,TeamDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<TeamDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public List<MemberListDTO> teamMemberlist(Long teamNum) {//팀num을 받아서 팀의 리스트를 뽑아준다.
        Team team = teamRepository.findTeamByTeamNum(teamNum);
        List<Member> memberList = memberRepository.findMembersByTeam(team);
        List<MemberListDTO> memberListDTOList = memberList.stream()//코드를 해석하면 memberList에 담긴 list 개수 만큼 for문을 돌릴것인데
                .map(member -> modelMapper.map(member, MemberListDTO.class)).collect(Collectors.toList());//member를 MemberDTO로 modelmapper를 할 것이고
        // 리스트 타입으로<toList()>로 바꿔줄 것임 ex)toList()가 아닌 toSet()으로 하면 처음 받은 List타입을 set타입으로 바꿔준다.


        return memberListDTOList;
    }

}