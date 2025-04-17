package com.project.my.entity.user.query;

import com.java.project.api.dto.response.SearchDto;
import com.java.project.api.entity.user.QUsers;
import com.java.project.api.entity.user.Users;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl {

  private final JPAQueryFactory query;
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
  private QUsers u = new QUsers("user");

  public Page<Users> findList(SearchDto dto, Pageable pageable){
    List<Users> usersLis = query
        .selectFrom(u)
        .where(startDateGoe(dto),endDateLoe(dto), roleEq(dto),keywordEq(dto))
        .orderBy(u.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = query
        .select(u.count()).from(u)
        .where(startDateGoe(dto),endDateLoe(dto), roleEq(dto),keywordEq(dto))
        .fetchOne();

    return new PageImpl<>(usersLis,pageable,total);
  }


  //기간별
  private BooleanExpression startDateGoe(SearchDto dto) {
      return dto.startDate() == null ? null : u.createdDate.goe(
          LocalDateTime.parse(dto.startDate(), formatter));
  }

  private BooleanExpression endDateLoe(SearchDto dto) {
      return dto.endDate() == null ? null : u.createdDate.loe(LocalDateTime.parse(dto.endDate(), formatter));
  }

  // 권한별 조회
  private Predicate roleEq(SearchDto dto){
    return dto.role() == null ? null : u.role.eq(dto.role());
  }

  // 검색어 조회
  private Predicate keywordEq(SearchDto dto) {

    if(dto.searchValue() != null){
      if(dto.searchType().equals("nickname"))
        return dto.searchValue() == null ? null : u.nickname.contains(dto.searchValue());
      if(dto.searchType().equals("email"))
        return dto.searchValue() == null ? null : u.email.contains(dto.searchValue());
      if(dto.searchType().equals("loginId"))
        return dto.searchValue() == null ? null : u.loginId.contains(dto.searchValue());
    }
    return null;
  }

}


