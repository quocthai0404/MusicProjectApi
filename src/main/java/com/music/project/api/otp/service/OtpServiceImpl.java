package com.music.project.api.otp.service;

import com.music.project.api.otp.dto.OtpRequestDto;
import com.music.project.api.otp.repository.OtpRepository;
import com.music.project.api.otp.result.VerificationResult;
import com.music.project.api.user.dto.ResetPasswordDTO;
import com.music.project.api.user.repository.UserRepository;
import com.music.project.entities.Otp;
import com.music.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OtpServiceImpl implements OtpService{

    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public VerificationResult verifyOtp(OtpRequestDto otpRequestDto) {
        Otp otp = otpRepository.findOtpRegister(otpRequestDto.otp, otpRequestDto.email);

        if (otp == null) {
            return VerificationResult.OTP_NOT_FOUND;
        }

        if (otp.getExpiresAt().getTime() <= System.currentTimeMillis()) {
            return VerificationResult.OTP_EXPIRED;
        }

        otp.setIsUsed(true);
        otpRepository.save(otp);

        User user = otp.getUser();
        if (user == null) {
            return VerificationResult.USER_NOT_FOUND;
        }

        user.setIsActive(true);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return VerificationResult.DATABASE_ERROR;
        }

        return VerificationResult.SUCCESS;
    }

    @Override
    @Transactional
    public VerificationResult verifyOtpForgetPassword(OtpRequestDto otpRequestDto) {
        Otp otp = otpRepository.findOtpForgetPassword(otpRequestDto.otp, otpRequestDto.email);

        if (otp == null) {
            return VerificationResult.OTP_NOT_FOUND;
        }

        if (otp.getExpiresAt().getTime() <= System.currentTimeMillis()) {
            return VerificationResult.OTP_EXPIRED;
        }

        otp.setIsUsed(true);
        otpRepository.save(otp);

        return VerificationResult.SUCCESS;
    }

    @Override
    @Transactional
    public VerificationResult verifyOtpResetPassword(ResetPasswordDTO resetPasswordDto) {
        Otp otp = otpRepository.findOtpResetPassword(resetPasswordDto.otp, resetPasswordDto.email);

        if (otp == null) {
            return VerificationResult.OTP_NOT_FOUND;
        }

        if (otp.getExpiresAt().getTime() <= System.currentTimeMillis()) {
            return VerificationResult.OTP_EXPIRED;
        }

        otp.setIsUsed(true);
        otpRepository.save(otp);

        return VerificationResult.SUCCESS;
    }

}
