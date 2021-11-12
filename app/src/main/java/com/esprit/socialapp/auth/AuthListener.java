package com.esprit.socialapp.auth;

import com.esprit.socialapp.entities.User;

public interface AuthListener {

    void OnAuthentication(User user);
}
