package net.manager.iym.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Builder
@Getter
@AllArgsConstructor
@ToString
public class NoticeBoard extends CommonEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeBoardNum")
    private Long noticeBoardNum;

    @Column(length = 100, nullable = false)
    private String noticeTitle;

    @ManyToOne
    @JoinColumn(name="id")
    private Member member;

    @Column(length = 3000, nullable = false)
    private String noticeContent;

    @Column(columnDefinition = "long default 0", nullable = false)
    private Long noticeVisitCount;


    public void addMember(Member member){ //홈페이지에서 멤버값을 받아 조인게시글 멤버컬럼에 삽입시 사용
        this.member = member;
    }

    public void change(String noticeTitle, String noticeContent){
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
    }

}
