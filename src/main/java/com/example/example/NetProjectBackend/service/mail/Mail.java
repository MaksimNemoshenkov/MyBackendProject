package com.example.NetProjectBackend.service.mail;

import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.Verify;

public interface Mail {
    void confirmationCode(String email);

    boolean recoveryCode(String email);

    boolean sendNewPassword(String password, User user, Verify verify);

    Verify readByCode(String code);

    void deleteCode(int ownerId);

    boolean checkData(Verify verify);
}
