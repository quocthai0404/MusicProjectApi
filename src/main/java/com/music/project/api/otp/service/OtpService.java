package com.music.project.api.otp.service;

import com.music.project.api.otp.dto.OtpRequestDto;
import com.music.project.api.otp.result.VerificationResult;

public interface OtpService {
    public VerificationResult verifyOtp(OtpRequestDto otpRequestDto);
}
