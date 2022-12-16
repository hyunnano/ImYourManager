package net.manager.iym.controller;


import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import net.manager.iym.domain.Schedule;
import net.manager.iym.domain.Team;
import net.manager.iym.dto.ScheduleDTO;
import net.manager.iym.repository.ScheduleRepository;
import net.manager.iym.service.ScheduleService;
import net.manager.iym.service.Validate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/main/team/schedule")
@Log4j2
@PreAuthorize("hasRole('TEAMMEMBER')")
public class ScheduleController {

//    private final ScheduleService scheduleService;
//    private final Validate validate;
//    private final ScheduleRepository scheduleRepository;
//    private final Team team;

    @PreAuthorize("hasRole('TEAMMEMBER')")
    @GetMapping(value = "/calendar")   //팀페이지에서 경기일정 눌렀을 때
    public void scheduleMain(){
//        JSONArray list = scheduleService.getJsonArray(team.getTeamNum());
//        log.info(list);
//        return list;  // json array로 내보내기.
//        return /main/team/schedule/calendar;
        //return "redirect:/main/team/schedule/calendar";
    }
//    @PreAuthorize("hasRole('TEAMLEADER')")
//    @PostMapping("/addSchedule")
//    public String newSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO, Errors errors, Model model) {
//
//        log.info(scheduleDTO);
//
//        if (errors.hasErrors()) {
//            //유효성 검사에 통과해지 못해도 입력 데이터값을 유지
//            model.addAttribute("scheduleDTO", scheduleDTO);
//
//            //유효성 검사 통과하지 못한 항목과 메세지 출력
//            Map<String, String> validResult = validate.validateHandling(errors);
//            for (String key : validResult.keySet()) {
//                model.addAttribute(key, validResult.get(key));
//                log.info(validResult);
//            }
//
//            return "schedule/addScheduleForm";   // 통과하지 못하면 다시 폼으로
//        }
//        scheduleService.register(scheduleDTO);
//
//
//        return "redirect:schedule/schedule_main"; // 등록성공하면 메인페이지로 이동.
//    }
//    @PostMapping("/readOne/{teamNum}/{scheduleNum}")
//    public ScheduleDTO selectOne(@RequestBody Map<String, String> scheduleNum){
//        ScheduleDTO scheduleDTO = scheduleService.readOne(Long.valueOf(scheduleNum.get("data")));
//        log.info(scheduleDTO);
//
//        return scheduleDTO;
//
//    }


}