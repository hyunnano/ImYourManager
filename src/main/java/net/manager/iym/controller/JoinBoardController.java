package net.manager.iym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.manager.iym.dto.JoinBoardDTO;

import net.manager.iym.dto.paging.PageRequestDTO;
import net.manager.iym.dto.paging.PageResponseDTO;
import net.manager.iym.security.handler.Custom403Handler;
import net.manager.iym.service.JoinBoardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller //빈을 만들어주고 컨트롤러라고 알려준다.
@RequestMapping("/main/joinboard")
@Log4j2
@RequiredArgsConstructor
public class JoinBoardController {//

    private final JoinBoardService joinBoardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){//타임리프를 위해 모델을 매개변수로 넣어줌

        PageResponseDTO<JoinBoardDTO> responseDTO =
                joinBoardService.list(pageRequestDTO); // 조인보드DTO 리스트를 담음

        log.info("리스폰스DTO값 : " +responseDTO);

        model.addAttribute("responseDTO", responseDTO); //담은 조인보드DTO 리스트를 뷰로 보내줌
    }
    @GetMapping("/register")//게시글 등록 컨트롤러 GET
    @PreAuthorize("hasRole('TEAMLEADER')")
    public void joinBoardRegisterGet(){

    }

    @GetMapping("/read")//게시글 확인 컨트롤러 GET
    public void read(Long joinBoardNum, PageRequestDTO pageRequestDTO, Model model){
        joinBoardService.updateJoinBoardNum(joinBoardNum);
        JoinBoardDTO joinBoardDTO = joinBoardService.read(joinBoardNum);
        log.info("joinBoardDTO의 값 확인 : "+joinBoardDTO);
        model.addAttribute("joinBoardDTO", joinBoardDTO);//모델에 joinBoardDTO 값을 담아준다.
    }
    @GetMapping("/modify")//게시글 수정 컨트롤러 GET
    public void modify(Long joinBoardNum, PageRequestDTO pageRequestDTO, Model model){
        JoinBoardDTO joinBoardDTO = joinBoardService.read(joinBoardNum);
        log.info("joinBoardDTO의 값 확인 : "+joinBoardDTO);
        model.addAttribute("joinBoardDTO", joinBoardDTO);//모델에 joinBoardDTO 값을 담아준다.
    }

    @PostMapping("/register")//조인 게시글 등록 컨트롤러 POST
    public String joinBoardRegisterPost(@Valid JoinBoardDTO joinBoardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
    log.info("-----(조인보드게시글등록post시작)joinBoardRegisterPost----");

    if (bindingResult.hasErrors()){
        log.info("/register에 결과값이 안넘어옴");//결과값이 바인딩안되면 실행됨
        return "redirect:/";
    }
    log.info(joinBoardDTO);
    joinBoardService.register(joinBoardDTO);
    return "redirect:/main/joinboard/list";
    }

    @PostMapping("/modify")//조인게시글 수정 컨트롤러 POST
    public String modify(@Valid JoinBoardDTO joinBoardDTO, PageRequestDTO pageRequestDTO,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            String link = pageRequestDTO.getLink();
            log.info("/modify에 결과값이 안넘어옴");//결과값이 바인딩안되면 실행됨
            return "redirect:/main/joinboard/modify?"+link;
        }
        joinBoardService.modify(joinBoardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("joinBoardNum", joinBoardDTO.getJoinBoardNum());//주소값을 지정해줌
     return "redirect:/main/joinboard/read";
    }
    @PostMapping("/remove")//조인 게시글 삭제 컨트롤러 POST
    public String remove(JoinBoardDTO joinBoardDTO, RedirectAttributes redirectAttributes){
        Long joinBoardNum = joinBoardDTO.getJoinBoardNum();
        joinBoardService.remove(joinBoardNum);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/main/joinboard/list";
    }
}
