/*
package com.project.my.auth.repository;

import com.project.my.auth.dto.AccessLogDTO;
import com.project.my.auth.dto.RecentLogDTO;
import com.project.my.auth.entity.AccessLog;
import com.project.my.auth.entity.ErrorCode;
import com.project.my.common.CryptoUtil;
import com.project.my.common.InputSanitizer;
import com.project.my.common.MaskingUtil;
import com.project.my.common.dto.FilterCondition;
import com.project.my.common.dto.PageRequestDTO;
import com.project.my.common.dto.SearchConditionBuilder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor // 생성자 자동 주입
public class AccessLogSearchImpl {
    private final JPAQueryFactory queryFactory;
    private final ErrorCodeRepository errorCodeRepository;
    private final CryptoUtil cryptoUtil;

    public Page<AccessLogDTO> searchList(PageRequestDTO pageRequestDTO) {
        QAccessLog accessLog = QAccessLog.accessLog;
        FilterCondition filter = pageRequestDTO.getFilter();
        String where = filter.getWhere();
        String keyword = filter.getKeyword();

        log.info("검색 필터 where = {}, keyword = {}", where, keyword);

        SearchConditionBuilder<AccessLog> scb = new SearchConditionBuilder<>(AccessLog.class, "accessLog");

        switch (where) {
            case "userNm" -> scb.addOrLike("userNm", keyword);
            case "userId" -> scb.addLike("userId", keyword);
            case "ipaddr" -> scb.addLike("ipaddr", keyword);
            //case "route" -> scb.addLike("route", keyword);
            case "route" -> {
                Map<String, Integer> routeMap = Map.of(
                        "모바일", 1,
                        "모바일", 2,
                        "공중망", 3
                );
                Integer routeCode = routeMap.get(keyword);
                if (routeCode != null) {
                    scb.addIn("route", List.of(routeCode));
                } else {
                    try {
                        int code = Integer.parseInt(keyword); // 유효한 숫자인 경우 문자열 검색 허용
                        scb.addIn("route", List.of(code));
                    } catch (NumberFormatException e) {
                        log.warn("잘못된 route 값: {}", keyword);
                    }
                }
            }
            case "errorCode" -> {
                List<Integer> codes = errorCodeRepository.findCodeIdsByCodeNmLike(keyword);
                scb.addIn("errCode", codes);
            }
            case "accessDate" -> {
                if (keyword.contains("/")) {
                    String[] parts = keyword.split("/");
                    if (parts.length == 2) {
                        scb.addBetween("date", parts[0].trim(), parts[1].trim());
                    }
                } else {
                    scb.addStartsWith("date", keyword);
                }
            }
        }

        BooleanBuilder builder = scb.build();

        String sortField = filter.getSortField();
        boolean asc = filter.isAsc();
        Set<String> sortable = Set.of("uid", "date", "userid", "userNm", "ipaddr");
        if (!sortable.contains(sortField)) sortField = "uid";

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                asc ? Sort.by(sortField).ascending() : Sort.by(sortField).descending()
        );

        PathBuilder<AccessLog> path = new PathBuilder<>(AccessLog.class, "accessLog");
        OrderSpecifier<?> orderSpecifier = asc
                ? path.getString(sortField).asc()
                : path.getString(sortField).desc();

        */
/**
         * 정렬은 조건이 조금만 복잡해져도 Pageable 의 Sort 기능을 사용하기 어렵다.
         * 루트 엔티티 범위를 넘어가는 동적 정렬 기능이 필요하면 스프링 데이터 페이징이 제공하는 Sort 를 사용하기 보다는
         * 파라미터를 직접 받아서 처리하는 것을 권장한다. - 김영한
         *//*


        List<AccessLog> resultList = queryFactory
                .selectFrom(accessLog)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetch();

        long totalCount = queryFactory
                .select(accessLog.count())
                .from(accessLog)
                .where(builder)
                .fetchOne();

        // ✅ errorCode 메시지 전체 Map으로 미리 조회 (N+1 제거)
        Map<Integer, String> errorMap = errorCodeRepository.findAll().stream()
                .collect(Collectors.toMap(ErrorCode::getCodeId, ErrorCode::getCodeNm));

        List<AccessLogDTO> dtoList = IntStream.range(0, resultList.size())
                .mapToObj(i -> {
                    AccessLog entity = resultList.get(i);
                    int no = (int) (totalCount - (pageable.getPageNumber() * pageable.getPageSize()) - i);
                    return toDTO(entity, no, errorMap);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, totalCount);
    }

    private AccessLogDTO toDTO(AccessLog entity, int no, Map<Integer, String> errorMap) {
        String errorMessage = errorCodeRepository
                .findMessageByCode(entity.getErrCode())
                .orElse(String.valueOf(entity.getErrCode()));

        return AccessLogDTO.builder()
                .no(no)
                .uid(cryptoUtil.encryptAES(String.valueOf(entity.getUid())))
                .ipaddr(MaskingUtil.ipAddressMasking(entity.getIpaddr()))
                .date(InputSanitizer.displayDate(entity.getDate()))
                .time(entity.getTime())
                .OS(entity.getOs())
                .browser(entity.getBrowser())
                .userid(MaskingUtil.idMasking(entity.getLoginId()))
                .userNm(MaskingUtil.letterMasking(entity.getUserNm()))
                .success(entity.getSuccess())
                .route(entity.getRoute())
                .errCode(entity.getErrCode())
                .errorMessage(errorMap.getOrDefault(entity.getErrCode(), String.valueOf(entity.getErrCode())))
                .build();
    }

    public List<RecentLogDTO> findRecentLogStats(String userId) {
        QAccessLog qAccessLog = QAccessLog.accessLog;

        List<Tuple> tuples = queryFactory
                .select(qAccessLog.date,
                        qAccessLog.time.max(),
                        qAccessLog.os,
                        qAccessLog.browser,
                        qAccessLog.count())
                .from(qAccessLog)
                .where(qAccessLog.userid.eq(userId))
                .groupBy(qAccessLog.date, qAccessLog.os, qAccessLog.browser)
                .orderBy(qAccessLog.accessDate.max().desc())
                .limit(5)
                .fetch();

        return tuples.stream().map(tuple -> RecentLogDTO.builder()
                .date(InputSanitizer.displayDate(tuple.get(qAccessLog.date)))
                .time(tuple.get(qAccessLog.time.max()))
                .os(tuple.get(qAccessLog.os))
                .browser(tuple.get(qAccessLog.browser))
                .count(tuple.get(qAccessLog.count()))
                .build()).collect(Collectors.toList());
    }

}
*/
