package net.manager.iym.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import net.manager.iym.domain.JoinBoard;
import net.manager.iym.domain.QJoinBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.util.List;

public class JoinBoardSearchImpl extends QuerydslRepositorySupport implements JoinBoardSearch {

    public JoinBoardSearchImpl(){
        super(JoinBoard.class);
    }

    @Override
    public Page<JoinBoard> search1(Pageable pageable) {

        QJoinBoard joinBoard = QJoinBoard.joinBoard;

        JPQLQuery<JoinBoard> query = from(joinBoard);

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

        booleanBuilder.or(joinBoard.joinTitle.contains("11")); // title like ...

        booleanBuilder.or(joinBoard.joinContent.contains("11")); // content like ....

        query.where(booleanBuilder);
        query.where(joinBoard.joinBoardNum.gt(0L));


        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<JoinBoard> list = query.fetch();

        long count = query.fetchCount();


        return null;

    }

    @Override
    public Page<JoinBoard> searchAll(String[] types, String keyword, Pageable pageable) {

        QJoinBoard joinBoard = QJoinBoard.joinBoard;
        JPQLQuery<JoinBoard> query = from(joinBoard);

        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(joinBoard.joinTitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(joinBoard.joinContent.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(joinBoard.member.id.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno > 0
        query.where(joinBoard.joinBoardNum.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<JoinBoard> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);

    }

}

















