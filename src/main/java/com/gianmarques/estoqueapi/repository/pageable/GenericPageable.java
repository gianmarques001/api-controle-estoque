package com.gianmarques.estoqueapi.repository.pageable;

import java.util.ArrayList;

public class GenericPageable<T> {

    private boolean first;
    private boolean last;
    private int number;
    private int size;
    private int numberOfElements;
    private int totalPages;
    private int totalElements;


    private ArrayList<T> content;

    public GenericPageable() {
    }

    public GenericPageable(boolean first, boolean last, int number, int size, int numberOfElements, int totalPages, int totalElements, ArrayList<T> content) {
        this.first = first;
        this.last = last;
        this.number = number;
        this.size = size;
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.content = content;
    }


    public int getSize() {
        return size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public ArrayList<T> getContent() {
        return content;
    }


}
