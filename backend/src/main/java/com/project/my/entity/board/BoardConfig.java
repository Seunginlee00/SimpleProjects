package com.project.my.entity.board;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "TB_BOARD_CONFIG")
public class BoardConfig {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "board_config_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private com.project.my.entity.enums.BoardType boardType; // 게시판 분류

    private Boolean isViewUse; // 조회수 사용여부

    private int topExpoCount; // 상단글 개수 여부


}
