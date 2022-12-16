package net.manager.iym.repository;

import lombok.extern.log4j.Log4j2;
import net.manager.iym.domain.Member;
import net.manager.iym.domain.MemberGrade;
import net.manager.iym.domain.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeamRepository teamRepository;
    @Test
    public void insertMember(){ //멤버 인서트 + 팀 강제 주입 테스트 성공
        Optional<Team> result = teamRepository.findById(2l);
        Team team = (Team)result.orElseThrow();//팀 null값 익셉션 해준 테스트
        IntStream.rangeClosed(11,20).forEach(i->{
            Member member = Member.builder().id("teammember"+i).team(team)
                    .pass(passwordEncoder.encode("1111"))
                    .mail("team"+i+"@test.com").gender("man").phone("010-1234-5678")
                    .name("teammember"+i).memberLoc("서울시 마포구")
                    .build();
            member.addGrade(MemberGrade.STANDARD);
            member.addGrade(MemberGrade.TEAMMEMBER);
//            member.addGrade(MemberGrade.TEAMLEADER);
//            member.addGrade(MemberGrade.ADMIN);
            memberRepository.save(member);
        });
    }

    @Test
    public void testRead(){
        Optional<Member> result = memberRepository.getWithGrade("member12");
        log.info("--------");
        log.info(result);
        Member member = result.orElseThrow();
        log.info("--------");
        log.info(member);
        log.info("--------");
        log.info(member.getGradeSet());
        log.info("--------");
        member.getGradeSet().forEach(memberGrade -> log.info(memberGrade.name()));
    }
    @Test
    public void testMemberInfo(){
        Member member =memberRepository.findMemberById("admin");
        log.info("admin 담긴 값 확인 : "+ member);
    }
    @Test
    public void testMemberTeamUpdate(){
        Team team = teamRepository.findTeamByTeamNum(3l);
        log.info("team :" + team);
        memberRepository.updateDeleteTeam(team);

    }
}
