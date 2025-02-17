package com.music.project.helpers.otp;

import java.util.Random;

public class OtpHelper {
    public static String generateOtp(int length) {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}
