package net.manager.iym.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.manager.iym.domain.Team;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {
    private final String id;
    private final String pass;
    private final String mail;
    private final String phone;
    private final String gender;
    private final String memberLoc;
    private final String name;
    private final Team team;

    //각각의 정보들을 매개변수로 받아서 주입해준다.
    public MemberSecurityDTO(String id, String pass,
                             String mail, String phone, String gender, String memberLoc, String name, Team team,Collection<? extends GrantedAuthority> authorities) {
        super(id, pass, authorities);//GrantedAuthority는 권한을 부여하여 권한을 받은 유저만 db를 이용할 수 있다.
        this.id = id;
        this.pass = pass;
        this.mail = mail;
        this.phone = phone;
        this.gender = gender;
        this.memberLoc = memberLoc;
        this.name = name;
        this.team = team;
    }
}
