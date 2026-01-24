package com.ichiban.ichitabi.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageHandler {

    // 전체 게시물 수
    private int totalCnt;
    // 한 페이지 내 게시물 수
    private int pageSize = 8;
    // 현재 네비게이션에서 표시할 페이지 수
    private int naviSize = 5;
    // 전체 페이지 수
    private int totalPage;
    // 현재 페이지
    private int page;
    // 네비게이션 시작 페이지
    private int beginPage;
    // 네비게이션 끝 페이지
    private int endPage;
    // 첫 페이지 여부 -> 이전 버튼 활성화 로직
    private boolean firstPage;
    // 마지막 페이지 여부 -> 다음 버튼 활성화 로직
    private boolean lastPage;

    public PageHandler(int totalCnt, int pageSize, int page) {
        this.totalCnt = totalCnt;
        this.pageSize = pageSize;
        this.page = page;

        calculate();
    }

    private void calculate() {
        // 전체 페이지 수
        totalPage = (int) Math.ceil((double) totalCnt / pageSize);

        // 현재 페이지 보정
        if (page < 1) {
            page = 1;
        } else if (page > totalPage && totalPage > 0) {
            page = totalPage;
        }

        // 네비게이션 시작 페이지
        beginPage = totalCnt == 0 ? 0 :
                ((page - 1) / naviSize) * naviSize + 1;

        // 네비게이션 끝 페이지
        endPage = Math.min(beginPage + naviSize - 1, totalPage);

        // 이전/다음 버튼 여부
        firstPage = (beginPage == 1);
        lastPage = (endPage == totalPage);
    }

    public int getOffset() {
        return (page - 1) * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }
}

