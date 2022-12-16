package net.manager.iym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.manager.iym.domain.Member;
import net.manager.iym.domain.Team;
import net.manager.iym.dto.*;
import net.manager.iym.dto.paging.PageRequestDTO;
import net.manager.iym.dto.paging.PageResponseDTO;
import net.manager.iym.service.TeamService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/main/team")
@RequiredArgsConstructor
@Log4j2
public class TeamController {
    private final TeamService teamService;

    @PreAuthorize("hasRole('STANDARD')")
    @GetMapping("/register")
    public void teamRegisterGET(){
        //로그인한 사람의 아이디를 받는다.
        //받은 아이디로 팀넘을 확인하고 있을시 돌려보낸다.
        //teamservice.teamcheck    isempty()
    }
    @GetMapping("/teammemberlist")
    public void teamMemberList(@RequestParam(value = "teamNum")Long teamNum, Model model){
        List<MemberListDTO> memberListDTOList = teamService.teamMemberlist(teamNum);
        model.addAttribute("memberListDTO", memberListDTOList);
    }
    @GetMapping("/list")
    public void teamList(PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO<TeamDTO> responseDTO =
                teamService.list(pageRequestDTO); // 조인보드DTO 리스트를 담음

        log.info("팀 리스폰스DTO값 : " +responseDTO);

        model.addAttribute("responseDTO", responseDTO); //담은 조인보드DTO 리스트를 뷰로 보내줌

    }
    @GetMapping("/read")
    public void teamRead(Long teamNum, PageRequestDTO pageRequestDTO, Model model){
        TeamDTO teamDTO = teamService.readOne(teamNum);
        log.info("teamDTO의 값 확인하기 : "+teamDTO);
        model.addAttribute("teamDTO", teamDTO);
    }
    @PostMapping("/teamjoin")
    public String teamJoin(@Valid TeamDTO teamDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("----팀가입 컨트롤러 시작----");
        log.info("팀조인디티오의 값 : "+teamDTO);
        teamService.teamJoin(teamDTO);

        return "redirect:/main/team/list";
    }


    @PostMapping("/register")
    public String teamRegisterPOST(@Valid TeamDTO teamDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
       log.info("-----팀생성 컨트롤러 시작-----");
        if (bindingResult.hasErrors()){
            log.info("/register에 결과값이 안넘어옴");//결과값이 바인딩안되면 실행됨
            return "redirect:/";
        }
        log.info(teamDTO);
        Long teamNum = teamService.register(teamDTO);
        log.info(teamNum);
        return "redirect:/main/team/list";
    }

    @GetMapping("/modify")
    public void modify(Long teamNum, PageRequestDTO pageRequestDTO, Model model){
        TeamDTO teamDTO = teamService.readOne(teamNum);
        log.info(teamDTO);
        model.addAttribute("teamDTO", teamDTO);
    }

    @PostMapping("/modify")//
    public String modify(@Valid TeamDTO teamDTO, PageRequestDTO pageRequestDTO,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            String link = pageRequestDTO.getLink();
            log.info("/modify에 결과값이 안넘어옴");//결과값이 바인딩안되면 실행됨
            return "redirect:/main/team/modify?"+link;
        }
        teamService.modify(teamDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("teamNum", teamDTO.getTeamNum());//주소값을 지정해줌
        return "redirect:/main/team/read";
    }

    @PostMapping("/remove")//조인 게시글 삭제 컨트롤러 POST
    public String remove(TeamDTO teamDTO, RedirectAttributes redirectAttributes){
        Long teamNum = teamDTO.getTeamNum();
        teamService.remove(teamNum);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/main/team/list";
    }
//    @PostMapping("/removeteammember")
//    @PreAuthorize("hasRole('TEAMLEADER')")
//    public String removeTeamMember(HttpServletRequest httpServletRequest){
//        String id = httpServletRequest.getParameter("id");
//         teamService.removeTeamMember(id);
//         return "redirect:/main/team/list";
//    }

}
