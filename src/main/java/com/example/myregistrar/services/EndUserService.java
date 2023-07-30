package com.example.myregistrar.services;

import com.example.myregistrar.models.EndUser;

public interface EndUserService {
    EndUser createUser(EndUser user);
    EndUser getUserByUsername(String username);
}
