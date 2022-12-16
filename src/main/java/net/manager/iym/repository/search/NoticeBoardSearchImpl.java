package net.manager.iym.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import net.manager.iym.domain.NoticeBoard;
import net.manager.iym.domain.QNoticeBoard;


import java.util.List;
import java.util.stream.Collectors;

public class NoticeBoardSearchImpl extends QuerydslRepositorySupport implements NoticeBoardSearch {

    public NoticeBoardSearchImpl() {
        super(NoticeBoard.class);
    }

    @Override
    public Page<NoticeBoard> search1(Pageable pageable) {

        QNoticeBoard noticeBoard = QNoticeBoard.noticeBoard;

        JPQLQuery<NoticeBoard> query = from(noticeBoard);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.or(noticeBoard.noticeTitle.contains("11"));

        booleanBuilder.or(noticeBoard.noticeContent.contains("11"));

        query.where(booleanBuilder);
        query.where(noticeBoard.noticeBoardNum.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<NoticeBoard> list = query.fetch();

        long count = query.fetchCount();


        return null;
    }

    @Override
    public Page<NoticeBoard> searchAll(String[] types, String keyword, Pageable pageable) {

        QNoticeBoard noticeBoard = QNoticeBoard.noticeBoard;
        JPQLQuery<NoticeBoard> query = from(noticeBoard);

        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(noticeBoard.noticeTitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(noticeBoard.noticeContent.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(noticeBoard.member.id.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno > 0
        query.where(noticeBoard.noticeBoardNum.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<NoticeBoard> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }


}
