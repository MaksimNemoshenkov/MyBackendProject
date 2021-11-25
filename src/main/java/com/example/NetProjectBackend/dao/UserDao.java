package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.dto.UserView;
import com.example.NetProjectBackend.models.enums.EStatus;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.UserListRequest;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    List<User> getAllSuitable(UserListRequest req);
    int create(User client);
    User readById(int id);
    User readByEmail(String Email);
    User readByName(String name);
    void update(User client);
    void delete(int id);
    void changeStatus(EStatus status, int id);
    void updatePassword(String password, int id);
    List<UserView> readPage(int limit, int offset, String status);
}
