package net.manager.iym.repository;

import net.manager.iym.domain.NoticeBoard;
import net.manager.iym.repository.search.NoticeBoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long>, NoticeBoardSearch {

    NoticeBoard findNoticeBoardByNoticeBoardNum(Long noticeBoardNum);

    @Modifying
    @Query("update NoticeBoard N set N.noticeVisitCount = N.noticeVisitCount +1 where N.noticeBoardNum= :noticeBoardNum")
    int updateNoticeVisitCount(@Param("noticeBoardNum") Long noticeBoardNum);

}
