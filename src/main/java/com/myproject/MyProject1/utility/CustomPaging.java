package com.myproject.MyProject1.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPaging <T>{
    private boolean next;
    private boolean prev;
    private int totalPages;
    private int currentPage;
    private long totalData;

    public static CustomPaging pageImpl(boolean first,boolean last,int totalPages,int currentPage,long totalData){
        CustomPaging<Object> paging=new CustomPaging<>();
        if(first==true && last==true){
            paging.setNext(false);
            paging.setPrev(false);
        }else if(first==true && last==false){
            paging.setPrev(false);
            paging.setNext(true);
        }else if(first==false && last==false){
            paging.setNext(true);
            paging.setPrev(true);
        }else if(first==false&&last==true){
            paging.setPrev(true);
            paging.setNext(false);
        }
        paging.setCurrentPage(currentPage);
        paging.setTotalPages(totalPages);
        paging.setTotalData(totalData);
        return paging;
    }
}
