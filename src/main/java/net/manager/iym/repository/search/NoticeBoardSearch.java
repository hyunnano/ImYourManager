package net.manager.iym.repository.search;

import net.manager.iym.domain.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeBoardSearch {
    Page<NoticeBoard> search1(Pageable pageable);

    Page<NoticeBoard> searchAll(String[] types, String keyword, Pageable pageable);

}
