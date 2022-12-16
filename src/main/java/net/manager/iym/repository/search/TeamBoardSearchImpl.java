package net.manager.iym.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import net.manager.iym.domain.QTeamBoard;
import net.manager.iym.domain.TeamBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TeamBoardSearchImpl extends QuerydslRepositorySupport implements TeamBoardSearch  {

    public TeamBoardSearchImpl(){
        super(TeamBoard.class);
    }
    @Override
    public Page<TeamBoard> search1(Pageable pageable) {
        QTeamBoard teamBoard = QTeamBoard.teamBoard;

        JPQLQuery<TeamBoard> query = from(teamBoard);

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

        booleanBuilder.or(teamBoard.teamBoardTitle.contains("11")); // title like ...

        booleanBuilder.or(teamBoard.teamBoardContent.contains("11")); // content like ....

        query.where(booleanBuilder);
        query.where(teamBoard.teamBoardNum.gt(0L));


        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<TeamBoard> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<TeamBoard> searchAll(String[] types, String keyword, Pageable pageable) {
        QTeamBoard teamBoard = QTeamBoard.teamBoard;
        JPQLQuery<TeamBoard> query = from(teamBoard);

        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(teamBoard.teamBoardTitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(teamBoard.teamBoardContent.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(teamBoard.member.id.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno > 0
        query.where(teamBoard.teamBoardNum.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<TeamBoard> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
