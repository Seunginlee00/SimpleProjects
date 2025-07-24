package com.project.my.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Pageable 과 PageRequest 는 Spring Data에서 제공하는 페이지네이션 정보를 담기 위한 인터페이스와 구현체이다.
 * 페이지 번호와 단일 페이지의 개수를 담을 수 있다.
 */

@Data
public class PageResponseDTO<E> {

    private List<E> dtoList;
    private List<Integer> pageNumList;
    private PageRequestDTO pageRequestDTO;
    private boolean prev, next;
    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {

        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int) totalCount;

        int page = Math.max(1, pageRequestDTO.getPage()); // 최소 1 이상 보장, 현재 페이지
        int size = pageRequestDTO.getSize(); // 한 페이지당 항목 수
        int blockSize = pageRequestDTO.getBlockSize(); // 페이지 번호 블록 수

        // 전체 페이지 수
        int lastPage = (int) Math.ceil((double) totalCount / size);

        // 현재 블록의 끝 페이지
        int end = (int) (Math.ceil(page / (double) blockSize)) * blockSize;

        // 시작 페이지
        int start = end - (blockSize - 1);
        end = Math.min(end, lastPage); // 실제 마지막 페이지와 비교

        this.prev = start > 1;
        this.next = totalCount > end * size;
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        if (prev) {
            this.prevPage = start - 1;
        }

        if (next) {
            this.nextPage = end + 1;
        }

        this.totalPage = lastPage;
        this.current = page;

    }
}
