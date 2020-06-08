package control;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenGenerator {

    private static String generateToken(String prefix, int length, boolean useLetters, boolean useNumbers) {
        return prefix + RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static String generateTokenForAccount() {
        return generateToken("", 10, true, true);
    }

    public static String generateProductId() {
        return generateToken("PID_", 5, false, true);
    }


    public static String generateOffCode() {
        return generateToken("OFF_", 5, true, true);
    }

    public static String generateRequestId() {
        return generateToken("", 8, false, true);
    }

    public static String generateAuctionId() {
        return generateToken("AUC_", 5, false, true);
    }

    public static String generateOrderId() {
        return generateToken("", 8, false, true);
    }

    public static String generateCommentId() {
        return generateToken("", 9, true, true);
    }

    public static String generateRateId() {
        return generateToken("", 9, true, true);
    }

    public static String generateCategoryId() {
        return generateToken("", 6, true, true);
    }

    public static String generateAttributeId() {
        return generateToken("", 10, false, true);
    }

}
