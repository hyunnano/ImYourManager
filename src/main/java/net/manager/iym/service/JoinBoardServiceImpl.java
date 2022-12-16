package net.manager.iym.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.manager.iym.controller.JoinBoardController;
import net.manager.iym.domain.JoinBoard;
import net.manager.iym.domain.Member;
import net.manager.iym.dto.JoinBoardDTO;

import net.manager.iym.dto.paging.PageRequestDTO;
import net.manager.iym.dto.paging.PageResponseDTO;

import net.manager.iym.repository.JoinBoardRepository;
import net.manager.iym.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service //자동으로 빈을 만들어주고 service 클래스라는것을 표현한다.
@Log4j2 //콘솔창에 설정한 정보들을 출력해준다
@RequiredArgsConstructor//final을 사용한 객체들을 이 클래스에 자동으로 의존주입을 해준다.
@Transactional //이 클래스를 실행하는 중에 오류가 생기면 처음으로 되돌려준다.
public class JoinBoardServiceImpl implements JoinBoardService {
    //@RequiredArgsConstructor에 의해서 자동으로 생성자가 주입된다.
    private final JoinBoardRepository joinBoardRepository;

    private final ModelMapper modelMapper;


    private final MemberRepository memberRepository;
    @Override
    public Long register(JoinBoardDTO joinBoardDTO) {//조인게시글 등록서비스
        JoinBoard joinBoard = dtoToEntity(joinBoardDTO);//받은 DTO를 entity로 변경
        Member member = memberRepository.findMemberById(joinBoardDTO.getId());//멤버에 html에서 받은 id 값을 넣어 조회한다.
        joinBoard.addMember(member); //변경한 Entity에는 멤버값이 없기 때문에 조인보드에 따로 멤버추가
        Long joinBoardNum = joinBoardRepository.save(joinBoard).getJoinBoardNum();//JPA를 사용하여 테이블에 값을 넣어주고 번호를 붙여준다.
        return joinBoardNum;
    }

    @Override
    public JoinBoardDTO read(Long joinBoardNum) {//제목 클릭시 조인보드num 받음
        JoinBoard joinBoard = joinBoardRepository.findJoinBoardByJoinBoardNum(joinBoardNum);//조인 보드로 레파지토리에서 보드값 불러옴
        JoinBoardDTO joinBoardDTO = EntityToDto(joinBoard);//불러온 조인보드값을 DTO로 변환시킴
        return joinBoardDTO;
    }

    @Override
    public void modify(JoinBoardDTO joinBoardDTO) {
        JoinBoard joinBoard = joinBoardRepository.findJoinBoardByJoinBoardNum(joinBoardDTO.getJoinBoardNum());//joinboardDTO에서 num을 받아와 게시물 검색
        joinBoard.changeTitleContentJoinType(joinBoardDTO.getJoinTitle(), joinBoardDTO.getJoinContent(), joinBoardDTO.getJoinType());//DTO로 받은 값으로 domain의 제목과 컨텐츠, 타입 수정
        joinBoardRepository.save(joinBoard);//save를 할 때 프라이머리 키가 있다면 업데이트로 들어간다.
    }

    @Override
    public void remove(Long joinBoardNum) {//게시물 삭제 서비스
        joinBoardRepository.deleteById(joinBoardNum);//넘버값으로 해당 조인게시물 삭제
    }

    @Override
    public PageResponseDTO<JoinBoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("joinBoardNum");

        Page<JoinBoard> result = joinBoardRepository.searchAll(types, keyword, pageable);

        List<JoinBoardDTO> dtoList = result.getContent().stream()
                .map(joinBoard -> EntityToDto(joinBoard)).collect(Collectors.toList());

        return PageResponseDTO.<JoinBoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public void updateJoinBoardNum(Long joinBoardNum) {
        joinBoardRepository.updateJoinVisitCount(joinBoardNum);
    }

}

