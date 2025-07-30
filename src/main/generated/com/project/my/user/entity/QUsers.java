package com.project.my.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = 1522559648L;

    public static final QUsers users = new QUsers("users");

    public final com.project.my.common.entity.QBaseEntity _super = new com.project.my.common.entity.QBaseEntity(this);

    public final NumberPath<Integer> access = createNumber("access", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> accessDate = createDateTime("accessDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> accessFailedCount = createNumber("accessFailedCount", Integer.class);

    public final NumberPath<Integer> admin = createNumber("admin", Integer.class);

    public final ListPath<com.project.my.board.entity.Board, com.project.my.board.entity.QBoard> boardList = this.<com.project.my.board.entity.Board, com.project.my.board.entity.QBoard>createList("boardList", com.project.my.board.entity.Board.class, com.project.my.board.entity.QBoard.class, PathInits.DIRECT2);

    public final StringPath chosung = createString("chosung");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Integer> display = createNumber("display", Integer.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDelete = createBoolean("isDelete");

    public final NumberPath<Integer> isTempPassword = createNumber("isTempPassword", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> lastLoginDate = createDateTime("lastLoginDate", java.time.LocalDateTime.class);

    public final StringPath loginId = createString("loginId");

    public final StringPath mobileNo = createString("mobileNo");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath passwd = createString("passwd");

    public final DateTimePath<java.time.LocalDateTime> passwdChangeDate = createDateTime("passwdChangeDate", java.time.LocalDateTime.class);

    public final ListPath<UserRoleList, QUserRoleList> roleList = this.<UserRoleList, QUserRoleList>createList("roleList", UserRoleList.class, QUserRoleList.class, PathInits.DIRECT2);

    public final StringPath salt = createString("salt");

    public final StringPath userNm = createString("userNm");

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}

