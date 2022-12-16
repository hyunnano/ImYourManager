package net.manager.iym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.manager.iym.dto.NoticeBoardDTO;
import net.manager.iym.dto.paging.PageRequestDTO;
import net.manager.iym.dto.paging.PageResponseDTO;
import net.manager.iym.service.NoticeBoardService;
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
@RequestMapping("/main/notice")
@Log4j2
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<NoticeBoardDTO> responseDTO = noticeBoardService.list(pageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/register")
    public void registerGET(){

    }

    @PostMapping("/register")
    public String registerPost(@Valid NoticeBoardDTO noticeBoardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("noticeboard POST register.......");
        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            return "redirect:/";
        }

        log.info(noticeBoardDTO);
        noticeBoardService.register(noticeBoardDTO);
        return "redirect:/main/notice/list";
    }

    @GetMapping("/read")
    public void read(Long noticeBoardNum, PageRequestDTO pageRequestDTO, Model model){
        noticeBoardService.updateNoticeBoardNum(noticeBoardNum);
        NoticeBoardDTO noticeBoardDTO = noticeBoardService.readOne(noticeBoardNum);
        log.info(noticeBoardDTO);
        model.addAttribute("noticeBoardDTO", noticeBoardDTO);

    }

    @GetMapping("/modify")
    public void modify(Long noticeBoardNum, PageRequestDTO pageRequestDTO, Model model){
        NoticeBoardDTO noticeBoardDTO = noticeBoardService.readOne(noticeBoardNum);
        log.info(noticeBoardDTO);
        model.addAttribute("noticeBoardDTO", noticeBoardDTO);
    }

    @PostMapping("/modify")
    public String modify(@Valid NoticeBoardDTO noticeBoardDTO, PageRequestDTO pageRequestDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            String link = pageRequestDTO.getLink();
            log.info("modify error...");
            return "redirect:/main/notice/modify?";
        }
        noticeBoardService.modify((noticeBoardDTO));
        redirectAttributes.addFlashAttribute("result","modified");
        redirectAttributes.addAttribute("noticeBoardNum",noticeBoardDTO.getNoticeBoardNum());
        return "redirect:/main/notice/read";
    }

    @PostMapping("remove")
    public String remove(NoticeBoardDTO noticeBoardDTO, RedirectAttributes redirectAttributes){
        Long noticeBoardNum = noticeBoardDTO.getNoticeBoardNum();
        noticeBoardService.remove(noticeBoardNum);
        redirectAttributes.addFlashAttribute("result","removed");
        return "redirect:/main/notice/list";
    }


}