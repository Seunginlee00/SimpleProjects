package com.project.my.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRoleList is a Querydsl query type for UserRoleList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserRoleList extends EntityPathBase<UserRoleList> {

    private static final long serialVersionUID = -2141448313L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRoleList userRoleList = new QUserRoleList("userRoleList");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUsers users;

    public final EnumPath<UserRole> usersRole = createEnum("usersRole", UserRole.class);

    public QUserRoleList(String variable) {
        this(UserRoleList.class, forVariable(variable), INITS);
    }

    public QUserRoleList(Path<? extends UserRoleList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRoleList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRoleList(PathMetadata metadata, PathInits inits) {
        this(UserRoleList.class, metadata, inits);
    }

    public QUserRoleList(Class<? extends UserRoleList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new QUsers(forProperty("users")) : null;
    }

}

