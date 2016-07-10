package com.example;


import org.seasar.extension.jdbc.JdbcManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MessageDao {
    @Autowired
    JdbcManager jdbcManager;

    public List<Message> selectAll() {
        return jdbcManager.from(Message.class).getResultList();
    }

    public List<Message> selectOrderByText() {
        return jdbcManager.selectBySqlFile(Message.class, "sql/message/selectOrderByText.sql").getResultList();
    }

    @Transactional
    public int insert(Message message) {
        return jdbcManager.insert(message).execute();
    }
}
