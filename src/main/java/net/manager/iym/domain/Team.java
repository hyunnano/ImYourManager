package net.manager.iym.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Builder
@Getter
@AllArgsConstructor
@ToString
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamNum")
    private Long teamNum;

    @Column(nullable = false)
    private String id;

    @Column(length = 20, nullable = false)
    private String teamName;

    @Column(length = 20, nullable = false)
    private String teamAge;

    @Column(length = 30, nullable = false)
    private String teamType;

    @Column(length = 50, nullable = false)
    private String teamLocal1;

    @Column(length = 50, nullable = false)
    private String teamLocal2;

    @Column(length = 20, nullable = false)
    private String teamLevel;

    @Column(length = 200)
    private String teamInfo;

    public void changeTeam(String teamName,String teamInfo, String teamAge,
         String teamType, String teamLevel, String teamLocal1, String teamLocal2
         ){
        this.teamName = teamName;
        this.teamInfo = teamInfo;
        this.teamAge = teamAge;
        this.teamType = teamType;
        this.teamLevel = teamLevel;
        this.teamLocal1 = teamLocal1;
        this.teamLocal2 = teamLocal2;
    }


}
