package com.project.my.board.entity;


import com.project.my.board.dto.BoardConfigDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "board_config")
public class BoardConfig {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "board_config_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private BoardType boardType; // 게시판 분류

    private Boolean isViewUse; // 조회수 사용여부

    private int topExpoCount; // 상단글 개수 여부


    public void update(BoardConfigDto configDto){
        if(configDto.isViewUse() != null)
            this.isViewUse = configDto.isViewUse();
        if(configDto.topExpoCount() != null)
            this.topExpoCount = configDto.topExpoCount();
    }


}
