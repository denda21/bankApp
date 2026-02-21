package com.bank_project.vo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PagedResult<T> {
    private final List<T> items;
    private final PagingDTO pagingDTO;

    public PagedResult(List<T> items, PagingDTO pagingDTO) {
        this.items = items;
        this.pagingDTO = pagingDTO;
    }
}
