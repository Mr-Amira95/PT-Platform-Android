package com.qtechnetworks.ptplatform.Model.utilits;

public interface Common {
    interface OTPListener {
        void onOTPReceived(String otp);
    }
}