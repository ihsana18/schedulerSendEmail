package com.myproject.MyProject1.utility;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MonthEnd {
    public static int monthEnd(){
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getActualMaximum(Calendar.DATE));
        return  cal.getActualMaximum(Calendar.DATE);
    }
}
