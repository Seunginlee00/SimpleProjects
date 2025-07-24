package com.project.my.common.dto;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SearchConditionBuilder<T> {

    private final PathBuilder<T> pathBuilder;
    private final BooleanBuilder builder = new BooleanBuilder();

    public SearchConditionBuilder(Class<T> clazz, String alias) {
        this.pathBuilder = new PathBuilder<>(clazz, alias);
    }
    /**
     * 해당 필드가 value와 일치(equal)하는 조건 추가.
     */
    public SearchConditionBuilder<T> addEqual(String field, Object value) {
        if (value != null) {
            builder.and(pathBuilder.get(field).eq(value));
        }
        return this;
    }

    /**
     * 해당 필드가 keyword를 포함(like)하는 조건 추가 (대소문자 무시).
     */
    public SearchConditionBuilder<T> addLike(String field, String keyword) {
        if (StringUtils.hasText(keyword)) {
            builder.and(pathBuilder.getString(field).containsIgnoreCase(keyword));
        }
        return this;
    }

    /**
     * 콤마(,) 또는 공백으로 구분된 여러 키워드 중 하나라도 포함하면 or 조건 추가.
     */
    public SearchConditionBuilder<T> addOrLike(String field, String keyword) {
        if (!StringUtils.hasText(keyword)) return this;
        List<String> keywords = Arrays.stream(keyword.split("[,\s]+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        BooleanBuilder orBuilder = new BooleanBuilder();
        for (String word : keywords) {
            orBuilder.or(pathBuilder.getString(field).containsIgnoreCase(word));
        }
        builder.and(orBuilder);
        return this;
    }

    /**
     * 해당 필드가 values에 포함된 값(in 조건)인지 확인.
     */
    public SearchConditionBuilder<T> addIn(String field, Collection<?> values) {
        if (values != null && !values.isEmpty()) {
            builder.and(pathBuilder.get(field).in(values));
        }
        return this;
    }

    /**
     * (yyyyMMdd) 형식의 문자열 범위 between 조건 추가.
     */
    public SearchConditionBuilder<T> addBetween(String field, String from, String to) {
        if (from != null && to != null && from.length() == 8 && to.length() == 8) {
            if (from.compareTo(to) > 0) {
                String temp = from;
                from = to;
                to = temp;
            }
            builder.and(pathBuilder.getString(field).between(from, to));
        }
        return this;
    }

    /**
     * 해당 필드가 특정 값으로 시작하는 조건 추가 (대소문자 무시).
     */
    public SearchConditionBuilder<T> addStartsWith(String field, String value) {
        if (StringUtils.hasText(value)) {
            builder.and(pathBuilder.getString(field).startsWithIgnoreCase(value));
        }
        return this;
    }

    /**
     * 해당 필드가 value보다 크거나 같은(>=) 조건 추가.
     */
    @SuppressWarnings("unchecked")
    public <V extends Comparable<? super V>> SearchConditionBuilder<T> addGreaterThanEqual(String field, V value) {
        if (value != null) {
            builder.and(
                    ((ComparableExpression<V>) pathBuilder.getComparable(field, value.getClass())).goe(value)
            );
        }
        return this;
    }

    /**
     * 여러 필드 중 하나라도 keyword를 포함하면 or 조건 추가.
     */
    public SearchConditionBuilder<T> addOrGroupLike(List<String> fields, String keyword) {
        if (!StringUtils.hasText(keyword)) return this;
        BooleanBuilder orGroup = new BooleanBuilder();
        for (String field : fields) {
            orGroup.or(pathBuilder.getString(field).containsIgnoreCase(keyword));
        }
        builder.and(orGroup);
        return this;
    }

    public BooleanBuilder build() {
        return builder;
    }
}

