package net.manager.iym.repository;

import lombok.extern.log4j.Log4j2;
import net.manager.iym.domain.Member;
import net.manager.iym.domain.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class TeamRepositoryTests {
    @Autowired
    private TeamRepository teamRepository;
@Autowired
private  MemberRepository memberRepository;
    @Test
    public void testRegisterTeam(){
        Team team = Team.builder()
                .teamAge("30대").teamName("ict").teamType("남성").teamLocal1("서울").teamLocal2("마포구").teamLevel("3")
                .teamInfo("안녕하세요 매주 축구합니다.")
                .build();
            teamRepository.save(team);
    }
    @Test
    public void testTeamMemberList(){
        log.info("팀원 불러오기 시작~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Team team = teamRepository.findTeamByTeamNum(1l);
        log.info("TEAM의 값 : "+team);
        List<Member> memberList = memberRepository.findMembersByTeam(team);
        log.info("팀원 불러오기의 값 : "+memberList);

    }
}
