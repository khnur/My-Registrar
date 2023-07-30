package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.exceptions.UserAlreadyExistsException;
import com.example.myregistrar.exceptions.UserNotFoundException;
import com.example.myregistrar.models.EndUser;
import com.example.myregistrar.repositories.EndUserRepo;
import com.example.myregistrar.services.EndUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EndUserServiceImpl implements EndUserService {
    private final EndUserRepo endUserRepo;

    @Transactional
    @Override
    public EndUser createUser(EndUser user) {
        if (user == null) {
            throw new UserNotFoundException("provided user is null");
        } else if (user.getId() != null) {
            throw new UserAlreadyExistsException("User with id=" + user.getId() + " already exists");
        }
        return endUserRepo.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public EndUser getUserByUsername(String username) {
        return endUserRepo.findEndUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("There is no user with such username=" + username));
    }
}
