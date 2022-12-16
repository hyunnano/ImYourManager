package net.manager.iym.repository;

import net.manager.iym.domain.JoinBoard;
import net.manager.iym.repository.search.JoinBoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JoinBoardRepository extends JpaRepository<JoinBoard, Long>, JoinBoardSearch {
    JoinBoard findJoinBoardByJoinBoardNum(Long joinBoardNum);//보드 넘버로 보드에 담긴 값 모두 불러옴

    @Modifying
    @Query("UPDATE JoinBoard J set J.joinVisitCount = J.joinVisitCount + 1 WHERE J.joinBoardNum = :joinBoardNum")
    int updateJoinVisitCount(@Param("joinBoardNum") Long joinBoardNum);
    //타입을 Long으로 하면 쿼리문을 리턴하는데 문제가 생기기 때문에 int의 값을 주어야함

}
