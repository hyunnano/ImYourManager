package net.manager.iym.repository;

import net.manager.iym.domain.TeamBoard;
import net.manager.iym.repository.search.TeamBoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TeamBoardRepository extends JpaRepository<TeamBoard, Long>, TeamBoardSearch {
    TeamBoard findTeamBoardByTeamBoardNum(Long teamBoardNum);//보드 넘버로 보드에 담긴 값 모두 불러옴

    @Modifying
    @Query("update TeamBoard TB set TB.teamBoardVisitCount = TB.teamBoardVisitCount +1 where TB.teamBoardNum= :teamBoardNum")
    int updateTeamBoardVisitCount(@Param("teamBoardNum") Long teamBoardNum);

}
