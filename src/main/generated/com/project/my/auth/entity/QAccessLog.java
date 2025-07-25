package com.project.my.auth.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccessLog is a Querydsl query type for AccessLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccessLog extends EntityPathBase<AccessLog> {

    private static final long serialVersionUID = 2024931227L;

    public static final QAccessLog accessLog = new QAccessLog("accessLog");

    public final com.project.my.common.entity.QBaseEntity _super = new com.project.my.common.entity.QBaseEntity(this);

    public final DateTimePath<java.time.LocalDateTime> accessDate = createDateTime("accessDate", java.time.LocalDateTime.class);

    public final StringPath browser = createString("browser");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath date = createString("date");

    public final StringPath day = createString("day");

    public final NumberPath<Integer> display = createNumber("display", Integer.class);

    public final NumberPath<Integer> errCode = createNumber("errCode", Integer.class);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final StringPath ipaddr = createString("ipaddr");

    public final StringPath loginId = createString("loginId");

    public final StringPath md = createString("md");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath month = createString("month");

    public final StringPath os = createString("os");

    public final NumberPath<Integer> route = createNumber("route", Integer.class);

    public final NumberPath<Integer> success = createNumber("success", Integer.class);

    public final StringPath time = createString("time");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath userNm = createString("userNm");

    public final StringPath year = createString("year");

    public final StringPath ym = createString("ym");

    public QAccessLog(String variable) {
        super(AccessLog.class, forVariable(variable));
    }

    public QAccessLog(Path<? extends AccessLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccessLog(PathMetadata metadata) {
        super(AccessLog.class, metadata);
    }

}

