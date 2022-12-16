package net.manager.iym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.manager.iym.dto.NoticeBoardDTO;
import net.manager.iym.dto.TeamBoardDTO;
import net.manager.iym.dto.paging.PageRequestDTO;
import net.manager.iym.dto.paging.PageResponseDTO;
import net.manager.iym.service.TeamBoardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/main/team/teamboard")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEAMMEMBER')")
public class TeamBoardController {

    private final TeamBoardService teamBoardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<TeamBoardDTO> responseDTO = teamBoardService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    @PreAuthorize("hasRole('TEAMMEMBER')")
    @GetMapping("/register")
    public void registerGet(){}

    @PostMapping("/register")
    public String registerPost(@Valid TeamBoardDTO teamBoardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("Teamboard POST register.......");

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            return "redirect:/main/team/teamboard/register";
        }
        log.info(teamBoardDTO);
       teamBoardService.register(teamBoardDTO);
        return "redirect:/main/team/teamboard/list";
    }

    @GetMapping("/read")//게시글 확인
    public void read(Long teamBoardNum, PageRequestDTO pageRequestDTO, Model model){
        teamBoardService.updateTeamBoardNum(teamBoardNum);
        TeamBoardDTO teamBoardDTO = teamBoardService.readOne(teamBoardNum);
        log.info("teamBoardDTO의 값 확인 : "+teamBoardDTO);
        model.addAttribute("teamBoardDTO", teamBoardDTO);
    }

    @GetMapping("/modify")
    public void modify(Long teamBoardNum, PageRequestDTO pageRequestDTO, Model model){
        TeamBoardDTO teamBoardDTO = teamBoardService.readOne(teamBoardNum);
        log.info(teamBoardDTO);
        model.addAttribute("teamBoardDTO", teamBoardDTO);
    }

    @PostMapping("/modify")//
    public String modify(@Valid TeamBoardDTO teamBoardDTO, PageRequestDTO pageRequestDTO,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            String link = pageRequestDTO.getLink();
            log.info("/modify에 결과값이 안넘어옴");//결과값이 바인딩안되면 실행됨
            return "redirect:/main/team/teamboard/modify?"+link;
        }
        teamBoardService.modify(teamBoardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("teamBoardNum", teamBoardDTO.getTeamBoardNum());//주소값을 지정해줌
        return "redirect:/main/team/teamboard/read";
    }
    @PostMapping("/remove")//조인 게시글 삭제 컨트롤러 POST
    public String remove(TeamBoardDTO teamBoardDTO, RedirectAttributes redirectAttributes){
        Long teamBoardNum = teamBoardDTO.getTeamBoardNum();
        teamBoardService.remove(teamBoardNum);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/main/team/teamboard/list";
    }
}
