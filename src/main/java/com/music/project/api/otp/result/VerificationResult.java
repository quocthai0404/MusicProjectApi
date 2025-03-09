package com.music.project.api.otp.result;

public enum  VerificationResult {
    SUCCESS,
    OTP_NOT_FOUND,
    OTP_EXPIRED,
    USER_NOT_FOUND,
    DATABASE_ERROR,
    NOT_MATCH_PASSWORD,;
}
