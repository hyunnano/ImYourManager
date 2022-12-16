package net.manager.iym.repository;

import net.manager.iym.domain.Schedule;
import net.manager.iym.dto.ScheduleDTO;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

//
//    List<Schedule> findAll(Long teamNum);
//
//    Optional<Schedule> findById(Long scheduleNum);
//
//    ScheduleDTO findScheduleDTOByScheduleNum(Long scheduleNum);
//
}