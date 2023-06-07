package com.myproject.MyProject1.utility;

public class AutoIncrementHelper {
    public static String increment(String id){
        String increment = "";

        if(id==null){
            increment="-001";
        }else {
            String lastId = id.substring(4);
            int lastNumber = Integer.parseInt(lastId)+1;
            if(lastNumber<=9){
                increment ="-00"+lastNumber;
            }else if(lastNumber<=99){
                increment="-0"+lastNumber;
            }else {
                increment="-"+lastNumber;
            }
        }
        return increment;
    }
}
