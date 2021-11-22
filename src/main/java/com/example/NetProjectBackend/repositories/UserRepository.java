package com.example.NetProjectBackend.repositories;

import com.example.NetProjectBackend.models.enums.EStatus;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.UserListRequest;

import java.util.List;

public interface UserRepository {
    public User create(User user);
    public User readById(int id);
    public User readByEmail(String email);
    public User readByName(String name);
    public User update(User user);
    public User delete(int id);
    public List<User> getAll();
    public List<User> getAllSuitable(UserListRequest req);
    void changeStatus(EStatus status, int id);
    void changePassword(User user, String password);
    User updatePassword(String password, int id);
}
