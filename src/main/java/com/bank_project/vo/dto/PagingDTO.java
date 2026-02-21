package com.bank_project.vo.dto;

public class PagingDTO {

    public static final int DEFAULT_PER_PAGE = 7;
    private final int total_row;
    private final int request_page;
    private final int row_per_page;

    public PagingDTO(int total_row, int request_page) {
        this(total_row, request_page, DEFAULT_PER_PAGE);
    }

    public PagingDTO(int total_row, int request_page, int row_per_page) {
        this.total_row = total_row;
        this.row_per_page = row_per_page;
        int maxPage = (total_row == 0) ? 1 : (int) Math.ceil((double) total_row / row_per_page);
        this.request_page = Math.max(1, Math.min(request_page, maxPage));
    }

    public int startIndex() {
        return (request_page - 1) * row_per_page;
    }

    public int getRowPerPage() {
        return row_per_page;
    }

    public int getTotalRow() {
        return total_row;
    }

    public int getRequestPage() {
        return request_page;
    }

    public int getTotalPages() {
        if (total_row == 0) {
            return 1;
        }
        return (int) Math.ceil((double) total_row / row_per_page);
    }

    public boolean hasPrevious() {
        return request_page > 1;
    }

    public boolean hasNext() {
        return request_page < getTotalPages();
    }

}
