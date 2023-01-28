package com.react.exam.dao;

import com.react.exam.serviceImpl.NewsCrawlImpl;
import com.react.exam.vo.DbSelectVo;

import java.sql.*;
import java.util.Map;

public class DbSelectDao {
    public DbSelectVo select(String key) throws SQLException {
        Map<String, String> env = System.getenv();
        Connection connection = DriverManager.getConnection(
                env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD")
        );
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM TEST_TABLE WHERE KEY_COL = ?");
        pstmt.setString(1, key);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        DbSelectVo dbSelectVo = new DbSelectVo(rs.getString("KEY_COL"), rs.getString("DATA_COL"));
        rs.close();
        pstmt.close();
        connection.close();

        return dbSelectVo;
    }

    NewsCrawlImpl nnImpl = new NewsCrawlImpl();

    public static void main(String[] args) throws SQLException {
        DbSelectDao dbSelectDao = new DbSelectDao();
        DbSelectVo testdata = dbSelectDao.select("0001");
        System.out.println(testdata.getData());
    }
}
