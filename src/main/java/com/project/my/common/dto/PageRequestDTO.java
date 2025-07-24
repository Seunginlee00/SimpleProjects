package com.project.my.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder  // - **상속 관계가 있는 클래스**에서 부모 클래스의 빌더 패턴을 지원하기 위해 사용된다.
@AllArgsConstructor  // 모든 필드에 대해 값을 받아 전체 매개변수 생성자를 자동으로 생성한다.
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default  // 값을 명시적으로 설정하지 않을 때만 기본값을 부여
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private Integer blockSize;  // Integer로 변경 (null 체크 가능하게)

    @Builder.Default
    private FilterCondition filter = new FilterCondition(); // filter NPE 예방

    public int getBlockSize() {
        // blockSize가 null이면 size와 동일하게 처리
        return (blockSize != null) ? blockSize : size;
    }
}
