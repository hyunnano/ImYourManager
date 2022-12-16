package net.manager.iym.repository.search;


import net.manager.iym.domain.JoinBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JoinBoardSearch {

    Page<JoinBoard> search1(Pageable pageable);

    Page<JoinBoard> searchAll(String[] types, String keyword, Pageable pageable);


}
