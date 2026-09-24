package com.mddvc.spring.app.dto;

import lombok.Getter;

import java.util.List;
@Getter
public class FinishedMatchesDto {
    private List<FinishedMatchDto> matches;
    private long currentPage;
    private long totalPages;

    public FinishedMatchesDto(List<FinishedMatchDto> matches, long currentPage, long totalPages) {
        this.matches = matches;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}
