package com.music.project.api.otp.service;

import com.music.project.api.otp.dto.OtpRequestDto;
import com.music.project.api.otp.result.VerificationResult;
import com.music.project.api.user.dto.ResetPasswordDTO;

public interface OtpService {
    public VerificationResult verifyOtp(OtpRequestDto otpRequestDto);
    public VerificationResult verifyOtpForgetPassword(OtpRequestDto otpRequestDto);
    public VerificationResult verifyOtpResetPassword(ResetPasswordDTO resetPasswordDto);
}
