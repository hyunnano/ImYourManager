package net.manager.iym.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.manager.iym.domain.Member;
import net.manager.iym.domain.Schedule;
import net.manager.iym.domain.Team;
import net.manager.iym.dto.ScheduleDTO;
import net.manager.iym.repository.MemberRepository;
import net.manager.iym.repository.ScheduleRepository;
import net.manager.iym.repository.TeamRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Log4j2
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
//    private final ModelMapper modelMapper;

//    private final ScheduleRepository scheduleRepository;
//    private final MemberRepository memberRepository;
//
//    public JSONArray getJsonArray(Long teamNum) {   // JSON 객체 배열로 각팀의 모든 스케줄을 반환해주는 메소드
//
//        List<Schedule> listAll = scheduleRepository.findAll(teamNum);//팀 번호로 스케쥴 모두 찾아와서 list에 저장.
//        log.info(listAll);
//
//        HashMap<String, String> info = new HashMap<>(); // Map 타입의 info 객체
//        JSONObject daily;    // JSONObject 타입의 daily 객체
//        JSONArray list = new JSONArray();  //JSONObject를 배열로 저장할 JSONArray 타입의 list 객체
//        for(int i= 0; i< list.length();i++) {
//            info.put("id", listAll.get(i).getScheduleNum().toString());
//            info.put("start",  listAll.get(i).getScheduleDate().toString().concat(listAll.get(i).getScheduleStartTime().toString()));
//            info.put("end", listAll.get(i).getScheduleDate().toString().concat(listAll.get(i).getScheduleEndTime().toString()));
//            info.put("url", listAll.get(i).getGround());
//            info.put("type", listAll.get(i).getPlayType());
//            daily = new JSONObject(info);
//            list.put(daily);
//            log.info(daily);
//        }
//        log.info(list);
//        return list;
//
//    }
//    @Override
//    public void register(ScheduleDTO scheduleDTO) {   // 스케줄 등록 처리
//
//
//        Optional<Member> member = memberRepository.findById(scheduleDTO.getId());   //스케줄에 있는 id로회원정보 가져오기
//        member.orElseThrow();
//        Team team = member.get().getTeam();  //가져온 회원 정보에서 team 객체 뽑아내기
//
//        Schedule schedule = Schedule.builder()   // DTO에 담겨온 정보를 entity에 하나씩  build
//                .scheduleNum(scheduleDTO.getScheduleNum())
//                .team(team)
//                .scheduleDate(LocalDateTime.parse(scheduleDTO.getScheduleDate()))
//                .scheduleStartTime(LocalTime.parse(scheduleDTO.getScheduleStartTime()))
//                .scheduleEndTime(LocalTime.parse(scheduleDTO.getScheduleEndTime()))
//                .ground(scheduleDTO.getGround())
//                .playType(scheduleDTO.getPlayType())
//                .build();    //스케줄 등록 폼에서 입력받은 값을  schedule 객체에 담기.
//        scheduleRepository.save(schedule);  // DB에 저장
//
//        Long teamNum = team.getTeamNum();   // 회원이 속한 팀의 번호 가져오기
//        getJsonArray(teamNum);   //팀번호로 스케쥴 모두 조회
//    }
//
//
//
//    @Override
//    public ScheduleDTO readOne(Long scheduleNum) {    // 스케줄 번호로 일정 하나 조회.
//        ScheduleDTO scheduleDTO= scheduleRepository.findScheduleDTOByScheduleNum(scheduleNum);
//        return scheduleDTO;
//    }
//
//    @Override
//    public void modify(ScheduleDTO scheduleDTO) {
//        Schedule schedule = Schedule.builder()
//                .scheduleDate(LocalDateTime.parse(scheduleDTO.getScheduleDate()))
//                .scheduleStartTime(LocalTime.parse(scheduleDTO.getScheduleStartTime()))
//                .scheduleEndTime(LocalTime.parse(scheduleDTO.getScheduleEndTime()))
//                .ground(scheduleDTO.getGround())
//                .playType(scheduleDTO.getPlayType())
//                .build();
//        getJsonArray(schedule.getScheduleNum());
//    }
//
//    @Override
//    public void remove(Long scheduleNum) {
//        Optional<Schedule> schedule = scheduleRepository.findById(scheduleNum);
//        //scheduleRepository.delete(scheduleNum);
//
//    }
//

}