package net.manager.iym.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.manager.iym.domain.Member;
import net.manager.iym.domain.TeamBoard;
import net.manager.iym.dto.TeamBoardDTO;
import net.manager.iym.dto.paging.PageRequestDTO;
import net.manager.iym.dto.paging.PageResponseDTO;
import net.manager.iym.repository.MemberRepository;
import net.manager.iym.repository.TeamBoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class TeamBoardServiceImpl implements TeamBoardService {

    private final ModelMapper modelMapper;

    private final TeamBoardRepository teamBoardRepository;

    private final MemberRepository memberRepository;

    @Override
    public Long register(TeamBoardDTO teamBoardDTO) {

        TeamBoard teamBoard = dtoToEntity(teamBoardDTO);//받은 DTO를 entity로 변경
        Member member = memberRepository.findMemberById(teamBoardDTO.getId());//멤버에 html에서 받은 id 값을 넣어 조회한다.
        teamBoard.addMember(member); //변경한 Entity에는 멤버값이 없기 때문에 조인보드에 따로 멤버추가
        Long teamBoardNum = teamBoardRepository.save(teamBoard).getTeamBoardNum();//JPA를 사용하여 테이블에 값을 넣어주고 번호를 붙여준다.
        return teamBoardNum;

    }

    @Override
    public TeamBoardDTO readOne(Long teamBoardNum) {
        TeamBoard teamBoard = teamBoardRepository.findTeamBoardByTeamBoardNum(teamBoardNum);
        TeamBoardDTO teamBoardDTO = entityToDTO(teamBoard);
        return teamBoardDTO;

    }

    @Override
    public void modify(TeamBoardDTO teamBoardDTO) {
        TeamBoard teamBoard = teamBoardRepository.findTeamBoardByTeamBoardNum(teamBoardDTO.getTeamBoardNum());
        teamBoard.changeTeamBoard(teamBoardDTO.getTeamBoardTitle(), teamBoardDTO.getTeamBoardContent());
        teamBoardRepository.save(teamBoard);

    }

    @Override
    public void remove(Long teamBoardNum) {
        teamBoardRepository.deleteById(teamBoardNum);

    }

    @Override
    public PageResponseDTO<TeamBoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("teamBoardNum");

        Page<TeamBoard> result = teamBoardRepository.searchAll(types, keyword, pageable);

        List<TeamBoardDTO> dtoList = result.getContent().stream()
                .map(teamBoard -> entityToDTO(teamBoard)).collect(Collectors.toList());


        return PageResponseDTO.<TeamBoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
    @Override
    public void updateTeamBoardNum(Long teamBoardNum){
        teamBoardRepository.updateTeamBoardVisitCount(teamBoardNum);
    }
}





