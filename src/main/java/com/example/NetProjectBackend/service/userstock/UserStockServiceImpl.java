package com.example.NetProjectBackend.service.userstock;

import com.example.NetProjectBackend.dao.KitchenwareDao;
import com.example.NetProjectBackend.dao.UserStockDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserStockServiceImpl implements UserStockService{
    private final UserStockDao userStockDao;

    public UserStockServiceImpl(UserStockDao userStockDao) {
        this.userStockDao = userStockDao;
    }


    @Override
    public ResponseEntity<?> readStock(int userId, int limit, int offset) {
        return ResponseEntity.ok(userStockDao.readStock(userId, limit, offset));
    }

    @Override
    public ResponseEntity<?> deleteStockElement(int userId, String ingredient) {
        int ingredientId = userStockDao.ingredientExist(ingredient);
        userStockDao.deleteStockElement(userId, ingredientId);
        return ResponseEntity.ok("Successfully deleted from the stock");
    }

    @Override
    public ResponseEntity<?> createStockElement(int userId, String ingredient, int amount) {
        int ingredientId = userStockDao.ingredientExist(ingredient);
        if (ingredientId == -1)
            return ResponseEntity.ok("Not found ingredient");
        if (userStockDao.readStockElement(userId, ingredientId) != null)
            return ResponseEntity.ok("Already exist in your stock");
        return ResponseEntity.ok(userStockDao.createStockElement(userId, ingredientId, amount));
    }

    @Override
    public ResponseEntity<?> updateStockElement(int userId, String ingredient, int amount) {
        int ingredientId = userStockDao.ingredientExist(ingredient);
        if (userStockDao.readStockElement(userId, ingredientId) == null)
            return ResponseEntity.ok("Not found in your stock");
        return ResponseEntity.ok(userStockDao.updateStockElement(userId, ingredientId, amount));
    }
}
