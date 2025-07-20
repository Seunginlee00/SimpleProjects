package com.project.my.entity.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardConfig is a Querydsl query type for BoardConfig
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardConfig extends EntityPathBase<BoardConfig> {

    private static final long serialVersionUID = -1929439211L;

    public static final QBoardConfig boardConfig = new QBoardConfig("boardConfig");

    public final EnumPath<com.project.my.entity.enums.BoardType> boardType = createEnum("boardType", com.project.my.entity.enums.BoardType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isViewUse = createBoolean("isViewUse");

    public final NumberPath<Integer> topExpoCount = createNumber("topExpoCount", Integer.class);

    public QBoardConfig(String variable) {
        super(BoardConfig.class, forVariable(variable));
    }

    public QBoardConfig(Path<? extends BoardConfig> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardConfig(PathMetadata metadata) {
        super(BoardConfig.class, metadata);
    }

}

