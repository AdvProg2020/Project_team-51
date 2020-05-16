package control;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenGenerator {

    private static String generateToken(String prefix,int length ,boolean useLetters, boolean useNumbers){
        return prefix + RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static String generateTokenForAccount(){
        return generateToken("" , 10 , true , true);
    }

    public static String generateProductId(){
        return generateToken("PID_" , 5 , false , true);
    }


    public static String generateOffCode(){
        return generateToken("OFF_" , 5 , true , true);
    }

    public static String generateRequestId(){
        return generateToken("REQ_" , 5 , false , true);
    }

    public static String generateAuctionId(){
        return generateToken("AUC_" , 5 , false , true);
    }


}