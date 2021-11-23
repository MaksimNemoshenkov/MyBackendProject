package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.dao.KitchenwareDao;
import com.example.NetProjectBackend.models.Kitchenware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
public class KitchenwareDaoImpl implements KitchenwareDao {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(KitchenwareDao.class);

    private static final String INSERT = "INSERT INTO KITCHENWARE (title, description, category, image_id, is_active) VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String SELECT = "SELECT id, title, description, category, image_id, is_active FROM KITCHENWARE WHERE id = ?";
    private static final String UPDATE = "UPDATE KITCHENWARE SET title = ?, description = ?, category = ?, image_id = ?, is_active = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM KITCHENWARE WHERE id = ?";
    private static final String SELECT_PAGE = "SELECT id, title, description, category, image_id, is_active FROM KITCHENWARE ORDER BY id ASC LIMIT ? OFFSET ?";

    public KitchenwareDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static Kitchenware mapKitchenwareRow(ResultSet rs, int rowNum) throws SQLException {
        return new Kitchenware(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getString("image_id"),
                rs.getBoolean("is_active")
        );
    }

    @Override
    public int create(Kitchenware kitchenware) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, kitchenware.getTitle());
                        ps.setString(2, kitchenware.getDescription());
                        ps.setString(3, kitchenware.getCategory());
                        ps.setString(4, kitchenware.getImage_id());
                        ps.setBoolean(5, kitchenware.is_active());
                        return ps;
                    }
                }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
        //jdbcTemplate.update(
        //        INSERT,
        //        kitchenware.getTitle(),
        //        kitchenware.getDescription(),
        //        kitchenware.getCategory(),
        //        kitchenware.getImage_id(),
        //        kitchenware.is_active(),
        //);
    }

    @Override
    public Kitchenware read(int id) {
        Kitchenware kitchenware = null;
        System.out.println("test");
        try {
            kitchenware = jdbcTemplate.queryForObject(SELECT, KitchenwareDaoImpl::mapKitchenwareRow, id);
        }
        catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Kitchenware with id {}", id);
        }
        return kitchenware;
    }

    @Override
    public void update(Kitchenware kitchenware) {
        jdbcTemplate.update(
                UPDATE,
                kitchenware.getTitle(),
                kitchenware.getDescription(),
                kitchenware.getCategory(),
                kitchenware.getImage_id(),
                kitchenware.is_active(),
                kitchenware.getId()
        );
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public List<Kitchenware> readPage(int limit, int offset) {
        List<Kitchenware> kitchenware = null;
        try {
            kitchenware = jdbcTemplate.query(SELECT_PAGE, KitchenwareDaoImpl::mapKitchenwareRow, limit, offset);
        }
        catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Kitchenware with limit {} and offset {}", limit, offset);
        }
        return kitchenware;
    }

}