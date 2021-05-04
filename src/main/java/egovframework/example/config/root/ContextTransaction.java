package egovframework.example.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ImportResource("classpath:/egovframework/spring/context-transaction.xml")
public class ContextTransaction {

    @Bean
    public DataSourceTransactionManager txManager(DataSource dataSource){
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(dataSource);
        return dstm;
    }
}
