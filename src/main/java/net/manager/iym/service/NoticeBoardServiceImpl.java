package net.manager.iym.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.manager.iym.domain.Member;
import net.manager.iym.domain.NoticeBoard;
import net.manager.iym.dto.NoticeBoardDTO;
import net.manager.iym.dto.paging.PageRequestDTO;
import net.manager.iym.dto.paging.PageResponseDTO;
import net.manager.iym.repository.MemberRepository;
import net.manager.iym.repository.NoticeBoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final ModelMapper modelMapper;

    private final NoticeBoardRepository noticeBoardRepository;

    private final MemberRepository memberRepository;

    @Override
    public Long register(NoticeBoardDTO noticeBoardDTO) {

        NoticeBoard noticeBoard = dtoToEntity(noticeBoardDTO);
        Member member = memberRepository.findMemberById(noticeBoardDTO.getId());//멤버에 html에서 받은 id 값을 넣어 조회한다.
        noticeBoard.addMember(member); //변경한 Entity에는 멤버값이 없기 때문에 조인보드에 따로 멤버추가
        Long noticeBoardNum = noticeBoardRepository.save(noticeBoard).getNoticeBoardNum();
        return noticeBoardNum;
    }

    @Override
    public NoticeBoardDTO readOne(Long noticeBoardNum) {

        NoticeBoard noticeBoard = noticeBoardRepository.findNoticeBoardByNoticeBoardNum(noticeBoardNum);
        NoticeBoardDTO noticeBoardDTO = entityToDTO(noticeBoard);
        return noticeBoardDTO;
    }

    @Override
    public void modify(NoticeBoardDTO noticeBoardDTO) {

        NoticeBoard noticeBoard = noticeBoardRepository.findNoticeBoardByNoticeBoardNum(noticeBoardDTO.getNoticeBoardNum());
        noticeBoard.change(noticeBoardDTO.getNoticeTitle(), noticeBoardDTO.getNoticeContent());
        noticeBoardRepository.save(noticeBoard);

    }

    @Override
    public void remove(Long noticeBoardNum) {

        noticeBoardRepository.deleteById(noticeBoardNum);

    }

    @Override
    public PageResponseDTO<NoticeBoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("noticeBoardNum");

        Page<NoticeBoard> result = noticeBoardRepository.searchAll(types, keyword, pageable);

        List<NoticeBoardDTO> dtoList = result.getContent().stream()
                .map(noticeBoard -> entityToDTO(noticeBoard)).collect(Collectors.toList());


        return PageResponseDTO.<NoticeBoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

    }

    @Override
    public void updateNoticeBoardNum(Long noticeBoardNum){
        noticeBoardRepository.updateNoticeVisitCount(noticeBoardNum);
    }

}

