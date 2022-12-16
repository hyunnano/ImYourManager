package net.manager.iym.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Builder
@Getter
@AllArgsConstructor
@ToString
public class JoinBoard extends CommonEntity{//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//DB의 컬럼값 1씩 증가를 따라서 자동적용
    @Column(name = "joinBoardNum")
    private Long joinBoardNum;
    @ManyToOne
    @JoinColumn(name="id")
    private Member member;
    @Column(length = 100, nullable = false)
    private String joinTitle;
    @Column(length = 3000, nullable = false)
    private String joinContent;
    @Column(length = 20, nullable = false)
    private String joinType;
    @Column(columnDefinition = "long default 0", nullable = false)
    private Long joinVisitCount;

    public void changeTitleContentJoinType(String joinTitle, String joinContent, String joinType){//글 수정작업시 사용
        this.joinTitle = joinTitle;
        this.joinContent = joinContent;
        this.joinType = joinType;

    }
    public void addMember(Member member){ //홈페이지에서 멤버값을 받아 조인게시글 멤버컬럼에 삽입시 사용
        this.member = member;
    }
}
