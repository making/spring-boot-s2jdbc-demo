package com.example;

import bitronix.tm.BitronixTransactionSynchronizationRegistry;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.jdbc.dialect.H2Dialect;
import org.seasar.extension.jdbc.manager.JdbcManagerImpl;
import org.seasar.extension.jdbc.meta.ColumnMetaFactoryImpl;
import org.seasar.extension.jdbc.meta.EntityMetaFactoryImpl;
import org.seasar.extension.jdbc.meta.PropertyMetaFactoryImpl;
import org.seasar.extension.jdbc.meta.TableMetaFactoryImpl;
import org.seasar.framework.convention.impl.PersistenceConventionImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionSynchronizationRegistry;

@Configuration
public class S2JdbcConfig {
    @Bean
    TransactionSynchronizationRegistry synchronizationRegistry(TransactionManager transactionManager) {
        return new BitronixTransactionSynchronizationRegistry();
    }

    @Bean
    JdbcManager jdbcManager(DataSource dataSource, TransactionSynchronizationRegistry syncRegistry) {
        PersistenceConventionImpl persistenceConvention = new PersistenceConventionImpl();
        ColumnMetaFactoryImpl columnMetaFactory = new ColumnMetaFactoryImpl();
        columnMetaFactory.setPersistenceConvention(persistenceConvention);
        PropertyMetaFactoryImpl propertyMetaFactory = new PropertyMetaFactoryImpl();
        propertyMetaFactory.setColumnMetaFactory(columnMetaFactory);
        propertyMetaFactory.setPersistenceConvention(persistenceConvention);
        TableMetaFactoryImpl tableMetaFactory = new TableMetaFactoryImpl();
        tableMetaFactory.setPersistenceConvention(persistenceConvention);
        EntityMetaFactoryImpl entityMetaFactory = new EntityMetaFactoryImpl();
        entityMetaFactory.setPersistenceConvention(persistenceConvention);
        entityMetaFactory.setPropertyMetaFactory(propertyMetaFactory);
        entityMetaFactory.setTableMetaFactory(tableMetaFactory);
        H2Dialect dialect = new H2Dialect();
        JdbcManagerImpl jdbcManager = new JdbcManagerImpl();
        jdbcManager.setDataSource(dataSource);
        jdbcManager.setDialect(dialect);
        jdbcManager.setEntityMetaFactory(entityMetaFactory);
        jdbcManager.setPersistenceConvention(persistenceConvention);
        jdbcManager.setSyncRegistry(syncRegistry);
        return jdbcManager;
    }
}
