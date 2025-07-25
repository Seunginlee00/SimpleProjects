package com.project.my.auth.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QErrorCode is a Querydsl query type for ErrorCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QErrorCode extends EntityPathBase<ErrorCode> {

    private static final long serialVersionUID = 174056560L;

    public static final QErrorCode errorCode = new QErrorCode("errorCode");

    public final NumberPath<Integer> codeId = createNumber("codeId", Integer.class);

    public final StringPath codeNm = createString("codeNm");

    public QErrorCode(String variable) {
        super(ErrorCode.class, forVariable(variable));
    }

    public QErrorCode(Path<? extends ErrorCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QErrorCode(PathMetadata metadata) {
        super(ErrorCode.class, metadata);
    }

}

