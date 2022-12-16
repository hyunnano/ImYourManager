package net.manager.iym.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Builder
@Getter
@AllArgsConstructor
@ToString
public class TeamBoard extends CommonEntity{////

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "teamBoardNum")
    private Long teamBoardNum;

    @ManyToOne
    @JoinColumn(name="id")
    private Member member;

    @Column(length = 100, nullable = false)
    private String teamBoardTitle;

    @Column(length = 3000, nullable = false)
    private String teamBoardContent;

    @Column(columnDefinition = "long default 0", nullable = false)
    private Long teamBoardVisitCount;

    @Column(length = 3000)
    private String teamBoardFile;

    public void addMember(Member member){ //홈페이지에서 멤버값을 받아 조인게시글 멤버컬럼에 삽입시 사용
        this.member = member;
    }
    public void changeTeamBoard(String teamBoardTitle, String teamBoardContent){//글 수정작업시 사용
        this.teamBoardTitle = teamBoardTitle;
        this.teamBoardContent = teamBoardContent;

    }
}
