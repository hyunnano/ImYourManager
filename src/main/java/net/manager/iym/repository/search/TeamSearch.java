package net.manager.iym.repository.search;


import net.manager.iym.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamSearch {

    Page<Team> search1(Pageable pageable);

    Page<Team> searchAll(String[] types, String keyword, Pageable pageable);


}
