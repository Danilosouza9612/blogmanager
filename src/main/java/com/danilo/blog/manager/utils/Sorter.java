package com.danilo.blog.manager.utils;

import org.springframework.data.domain.Sort;

public class Sorter {
    private final String columnName;
    private final SorterDirection sorterDirection;

    public Sorter(String columnName, String sorterDirection){
        this.columnName = columnName;
        if (sorterDirection.equals("ASC")) {
            this.sorterDirection = SorterDirection.ASC;
        } else {
            this.sorterDirection = SorterDirection.DESC;
        }
    }


    public Sort getSort(){
        Sort sort = Sort.by(columnName);
        switch (sorterDirection){
            case ASC -> sort = sort.ascending();
            case DESC -> sort = sort.descending();
        }
        return sort;
    }
}
