package com.myproject.MyProject1.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomPage <T>{
    private Object data;
    private String status;
    private boolean success;
    private CustomPaging paging;
}
