package net.manager.iym.repository.search;

import net.manager.iym.domain.TeamBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamBoardSearch {
    Page<TeamBoard> search1(Pageable pageable);

    Page<TeamBoard> searchAll(String[] types, String keyword, Pageable pageable);

}
