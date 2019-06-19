package utils;

import java.util.UUID;

public class ActiveCodeUtils {
    public static String creatActiveCode(){
        return UUID.randomUUID().toString() ;
    }
}
