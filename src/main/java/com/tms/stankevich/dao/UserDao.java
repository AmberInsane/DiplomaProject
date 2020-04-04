package com.tms.stankevich.dao;

import com.tms.stankevich.domain.user.User;

import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {
    @Override
    public Optional<User> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }
}
