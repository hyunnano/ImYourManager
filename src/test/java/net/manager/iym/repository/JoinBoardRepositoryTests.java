package net.manager.iym.repository;

import lombok.extern.log4j.Log4j2;
import net.manager.iym.domain.JoinBoard;
import net.manager.iym.domain.Member;
import net.manager.iym.dto.JoinBoardDTO;
import net.manager.iym.service.JoinBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class JoinBoardRepositoryTests {
    @Autowired
    private JoinBoardRepository joinBoardRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Test
    public void testJoinBoardInsert(){
        Member member = memberRepository.findMemberById("bjy961206");
        IntStream.rangeClosed(1,5).forEach(i->{
            JoinBoard joinBoard = JoinBoard.builder()
                    .joinTitle("테스트"+i).joinContent("테스트"+i+"글내용")
                    .member(member).joinVisitCount(0l)
                    .joinType("테스트타입"+i).build();
            JoinBoard joinBoardResult = joinBoardRepository.save(joinBoard);
            log.info("JoinBoardNum : " + joinBoardResult.getJoinBoardNum());
        });
    }
    @Test
    public void testJoinBoardReadOne(){//
        JoinBoard joinBoard = joinBoardRepository.findJoinBoardByJoinBoardNum(17l);
        JoinBoardDTO joinBoardDTO = JoinBoardDTO.builder().joinBoardNum(joinBoard.getJoinBoardNum())
                .joinTitle(joinBoard.getJoinTitle()).joinContent(joinBoard.getJoinContent())
                .joinVisitCount(joinBoard.getJoinVisitCount())
                .joinType(joinBoard.getJoinType()).id(joinBoard.getMember().getId()).regDate(joinBoard.getRegDate()).build();
        log.info(joinBoardDTO);
    }

    @Test
    public void testJoinBoardList(){
        List<JoinBoard> list = joinBoardRepository.findAll();
        log.info(list);
    }
}
