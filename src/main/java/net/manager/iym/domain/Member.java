package net.manager.iym.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@AllArgsConstructor
@Builder
@ToString
public class Member extends CommonEntity{//

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "teamNum")
    private Team team;

    @Column(nullable = false)
    private String pass;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String memberLoc;

    @ElementCollection(fetch = FetchType.EAGER)//
    @Builder.Default
    private Set<MemberGrade> gradeSet = new HashSet<>();

    public void changeTeam(Team team) {
        this.team = team;
    }

    public void changePass(String pass) {
        this.pass = pass;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeMail(String mail) {
        this.mail = mail;
    }

    public void changePhone(String phone) {
        this.phone = phone;
    }

    public void changeGender(String gender) {
        this.gender = gender;
    }

    public void changeMemberLoc(String memberLoc) {
        this.memberLoc = memberLoc;
    }

    public void addGrade(MemberGrade memberGrade){
        this.gradeSet.add(memberGrade);
    }

    public void removeGrade(MemberGrade memberGrade) {this.gradeSet.remove(memberGrade);}

    public void clearGrade(){
        this.gradeSet.clear();
    }

    public void change(String pass, String mail, String phone, String memberLoc){
        this.pass = pass;
        this.mail = mail;
        this.phone = phone;
        this.memberLoc = memberLoc;
    }
}

