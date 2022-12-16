package net.manager.iym.service;

import lombok.extern.log4j.Log4j2;
import net.manager.iym.domain.JoinBoard;
import net.manager.iym.domain.Member;
import net.manager.iym.domain.MemberGrade;
import net.manager.iym.dto.JoinBoardDTO;


import net.manager.iym.repository.JoinBoardRepository;
import net.manager.iym.repository.JoinBoardRepositoryTests;
import net.manager.iym.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class JoinBoardServiceTests {

    @Autowired
    JoinBoardService joinBoardService;
    @Autowired
    MemberRepository memberRepository; //
    @Autowired
    JoinBoardRepository joinBoardRepository;
//    @Autowired
//    JoinBoardQueryRepository joinBoardQueryRepository;

    @Test
    public void testRegister() {//회원값을 받아서 조인게시글에 id값 삽입하여 게시글 등록 성공
        Member member = memberRepository.findMemberById("member1");//멤버에 bjy961206이 가지고 있는 값을 받아옴
        log.info("-----------------member----------------");
        log.info(member);
        JoinBoardDTO joinBoardDTO = JoinBoardDTO.builder()
                .joinTitle("서비스테스트6").joinContent("서비스테스트글내용6")
                .id(member.getId()).joinVisitCount(0l).joinType("서비스테스트타입6")
                .build();//DTO작업 즉, registet.html에서 받아 넣어줄 값들 생성
        log.info("---------------joinBoardDTO------------------");
        log.info(joinBoardDTO);
        joinBoardService.register(joinBoardDTO);//DTO값을 service의 register 작업을 통해 조인게시글에 넣어준다.
    }

    @Test
    public void testJoinBoardRead() {//게시글 조회 성공
        JoinBoard joinBoard = joinBoardRepository.findJoinBoardByJoinBoardNum(18l);
        log.info(joinBoard);
    }

    @Test
    public void testJoinBoardModify(){//게시글 수정 성공
        JoinBoard joinBoard = joinBoardRepository.findJoinBoardByJoinBoardNum(17l);
        joinBoard.changeTitleContentJoinType("제목수정TDD", "글수정TDD", "타입수정TDD");
        joinBoardRepository.save(joinBoard);
    }
    @Test
    public void testJoinBoardRemove(){//게시글 제거 성공
        joinBoardRepository.deleteById(18l);
    }
//    @Test
//    public ResponseEntity<JoinBoardListDTO> testJoinBoardList(Pageable pageable){
//        Page<JoinBoard> results = joinBoardQueryRepository.getJoinBoardList(pageable);
//
//        return new ResponseEntity<>(JoinBoardListDTO.builder()
//                .joinBoardList(results.getContent())
//                .totalCount(results.getTotalElements()).totalPages((long)results.getTotalPages()).build(), HttpStatus.OK);
//    }
}
