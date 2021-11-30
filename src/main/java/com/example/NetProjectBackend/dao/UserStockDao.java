package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.UserStockElement;

import java.util.List;


public interface UserStockDao {
    List<UserStockElement> readStock(int userId, int limit, int offset);

    void deleteStockElement(int userId, int ingredientId);

    UserStockElement createStockElement(int userId, int ingredientId, int amount);

    int ingredientExist(String ingredient);

    UserStockElement readStockElement(int userId, int ingredientId);

    UserStockElement updateStockElement(int userId, int ingredientId, int amount);
}
