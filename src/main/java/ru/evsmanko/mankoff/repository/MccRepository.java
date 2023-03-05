package ru.evsmanko.mankoff.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.evsmanko.mankoff.dto.MccDto;
import ru.evsmanko.mankoff.entity.MCCInfoEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class MccRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MccRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String SQL_SELECT_ALL = "SELECT * FROM MCC";

    private final String SQL_SELECT_BY_ID = "SELECT * FROM MCC WHERE id = ?";

    private final String SQL_INSERT = "INSERT INTO mcc (id, mcc_code, mcc_name, mcc_code_description, cashback_percent) VALUES (?, ?, ?, ?, ?)";

    private MCCInfoEntity mapRowToMccEntity(ResultSet rs, int rowNum) throws SQLException {
        MCCInfoEntity mccInfoEntity = new MCCInfoEntity();

        mccInfoEntity.setId(rs.getLong("id"));
        mccInfoEntity.setMccCode(rs.getLong("mcc_code"));
        mccInfoEntity.setMccName(rs.getString("mcc_name"));
        mccInfoEntity.setMccCodeDescription(rs.getString("mcc_code_description"));
        mccInfoEntity.setCashbackPercent(rs.getInt("cashback_percent"));

        return mccInfoEntity;
    }

    public List<MCCInfoEntity> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, this::mapRowToMccEntity);
    }

    public Optional<MCCInfoEntity> getMccEntityById(long id) {
        List<MCCInfoEntity> result = jdbcTemplate.query(SQL_SELECT_BY_ID, this::mapRowToMccEntity, id);
        return result.size() > 0 ? Optional.of(result.get(0)) : Optional.empty();
    }

    public Optional<MCCInfoEntity> save(MccDto mcc) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int affectedRowNumber = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT, new String[]{"id"});

                preparedStatement.setLong(1, mcc.getId());
                preparedStatement.setLong(2, mcc.getMccCode());
                preparedStatement.setString(3, mcc.getMccName());
                preparedStatement.setString(4, mcc.getMccCodeDescription());
                preparedStatement.setInt(5, mcc.getCashbackPercent());

                return preparedStatement;
            }
        }, keyHolder);

        if (affectedRowNumber == 0 || keyHolder.getKey() == null) {
            return Optional.empty();
        }

        Number key = keyHolder.getKey();
        if (key == null) {
            return Optional.empty();
        }
        Optional<MCCInfoEntity> mccEntity = this.getMccEntityById(key.longValue());

        return mccEntity;
    }
}