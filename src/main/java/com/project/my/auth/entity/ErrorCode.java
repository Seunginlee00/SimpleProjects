package com.project.my.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "error_code")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorCode {

    @Id
    @Column(name = "code_id")  // 명시적이지만 생략 가능 (동일한 이름이면 자동 매핑됨)
    private Integer codeId;

    @Column(name = "code_nm")  // 생략 가능
    private String codeNm;
}
