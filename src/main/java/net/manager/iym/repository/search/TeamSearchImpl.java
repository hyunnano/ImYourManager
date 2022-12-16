package net.manager.iym.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import net.manager.iym.domain.Team;
import net.manager.iym.domain.QTeam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TeamSearchImpl extends QuerydslRepositorySupport implements TeamSearch {

    public TeamSearchImpl(){
        super(Team.class);
    }

    @Override
    public Page<Team> search1(Pageable pageable) {

        QTeam team = QTeam.team;

        JPQLQuery<Team> query = from(team);

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

        booleanBuilder.or(team.teamName.contains("11")); // teamName like ...

        booleanBuilder.or(team.teamInfo.contains("11")); // teamInfo like ....

        query.where(booleanBuilder);
        query.where(team.teamNum.gt(0L));


        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Team> list = query.fetch();

        long count = query.fetchCount();


        return null;

    }

    @Override
    public Page<Team> searchAll(String[] types, String keyword, Pageable pageable) {

        QTeam team = QTeam.team;
        JPQLQuery<Team> query = from(team);

        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(team.teamName.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(team.teamInfo.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(team.teamLevel.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno > 0
        query.where(team.teamNum.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Team> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);

    }

}

















