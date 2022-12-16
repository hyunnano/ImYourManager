package net.manager.iym.security.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.manager.iym.domain.Member;
import net.manager.iym.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional//하나씩만 실행하도록 설정해준다.
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {//인터페이스를 연결해주고 한개의 메소드를 오버라이딩해준다.

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        log.info("받은 id명(loadUserByUserName) : " + id);
        Optional<Member> result = memberRepository.getWithGrade(id);//id를 grade와 함께 가져와서 담아준다.
        if(result.isEmpty()){
            throw new UsernameNotFoundException("User를 찾을 수 없음(loadUserByUserName오류)");
        }
        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                member.getId(), member.getPass(), member.getMail(),
                member.getPhone(), member.getGender(), member.getMemberLoc(),
                member.getName(), member.getTeam(),
                member.getGradeSet().stream().map(memberGrade ->
                                new SimpleGrantedAuthority("ROLE_"+memberGrade.name()))
                .collect(Collectors.toList()));
        log.info("---memberSecurityDTO 작동 시점확인---" +memberSecurityDTO);
    return memberSecurityDTO; //컨트롤러에게 던져준다.
    }
//    public Collection<? extends GrantedAuthority> getAuthorities() {  //권한 만들기 모르겠음!
//
//        Member member;
//        Collection<GrantedAuthority> collect = new ArrayList<>();
//        collect.add(new GrantedAuthority() {
//
//            public String getAuthority() {
//                return member.getRole().toString();
//            }
//        });
//        return collect;
//    }



}
