package egovframework.example.config.root;

import egovframework.rte.psl.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class ContextSqlMap {
    @Bean
    public SqlMapClientFactoryBean sqlMapClient(DataSource dataSource){
        SqlMapClientFactoryBean             smcfb = new SqlMapClientFactoryBean();
        PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();

        smcfb.setConfigLocation(pmrpr.getResource("classpath:/egovframework/sqlmap/example/sql-map-config.xml"));
        smcfb.setDataSource(dataSource);

        return smcfb;
    }
}
