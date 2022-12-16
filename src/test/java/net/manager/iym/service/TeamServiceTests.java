package net.manager.iym.service;

import lombok.extern.log4j.Log4j2;
import net.manager.iym.domain.Member;
import net.manager.iym.domain.MemberGrade;
import net.manager.iym.domain.Team;
import net.manager.iym.dto.MemberListDTO;
import net.manager.iym.repository.MemberRepository;
import net.manager.iym.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Log4j2
public class TeamServiceTests {
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Test
    public void testjoinTeam(){

    }
    @Test
    public void testTeamList(){
       List<MemberListDTO> memberList = teamService.teamMemberlist(1l);
       log.info("팀에 들어갔을 때 그 팀의 팀원리스트 : "+memberList);
    }
    @Test
    public void testTeamRemove() {
        teamService.remove(1l);

    }
    @Test
    public void testRemoveTeamMember(){
        teamService.removeTeamMember("member1");
    }
}
