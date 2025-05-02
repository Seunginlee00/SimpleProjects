package com.project.my.entity.user.query;

import com.project.my.dto.response.SearchDto;
import com.project.my.entity.user.QUsers;
import com.project.my.entity.user.Users;
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
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private QUsers u = new QUsers("user");

  public Page<Users> findList(SearchDto dto, Pageable pageable){
    List<Users> usersLis = query
        .selectFrom(u)
        .where(startDateGoe(dto),endDateLoe(dto), roleEq(dto),keywordEq(dto),deleteNo())
        .orderBy(u.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = query
        .select(u.count()).from(u)
        .where(startDateGoe(dto),endDateLoe(dto), roleEq(dto),keywordEq(dto),deleteNo())
        .fetchOne();

    return new PageImpl<>(usersLis,pageable,total);
  }

  // 삭제 된거 제외
  private Predicate deleteNo() {
    return u.isDelete.eq(Boolean.FALSE);
  }

  //기간별
  private BooleanExpression startDateGoe(SearchDto dto) {
    if (dto.startDate() == null) {
      return null;
    }
    // 날짜만 주어졌을 경우, 시간을 기본값 00:00:00으로 설정
    String startDate = dto.startDate() + "T00:00:00";
    return u.createdDate.goe(LocalDateTime.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
  }

  private BooleanExpression endDateLoe(SearchDto dto) {
    if (dto.endDate() == null) {
      return null;
    }
    // 날짜만 주어졌을 경우, 시간을 기본값 23:59:59으로 설정
    String endDate = dto.endDate() + "T23:59:59";
    return u.createdDate.loe(LocalDateTime.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
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


