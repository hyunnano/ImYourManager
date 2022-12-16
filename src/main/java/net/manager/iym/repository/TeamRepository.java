package net.manager.iym.repository;

import net.manager.iym.domain.Team;
import net.manager.iym.dto.TeamDTO;
import net.manager.iym.repository.search.TeamSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamSearch {
    Team findTeamByTeamNum(Long teamNum);

    @Modifying
    @Transactional
    @Query("delete from Team m where m.teamNum = :teamNum")
    void deleteByTeamNum(Long teamNum);
}
