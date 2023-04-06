package org.egovframe.config.root;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
public class ContextTransaction {

    @Bean
    public DataSourceTransactionManager txManager(DataSource dataSource){
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(dataSource);
        return dstm;
    }


    @Bean
    public TransactionInterceptor txAdvice(DataSourceTransactionManager txManager) {
        TransactionInterceptor txAdvice = new TransactionInterceptor();
        txAdvice.setTransactionManager(txManager);
        txAdvice.setTransactionAttributeSource(getNameMatchTransactionAttributeSource());
        return txAdvice;
    }

    private NameMatchTransactionAttributeSource getNameMatchTransactionAttributeSource() {
        NameMatchTransactionAttributeSource txAttributeSource = new NameMatchTransactionAttributeSource();
        txAttributeSource.setNameMap(getRuleBasedTxAttributeMap());
        return txAttributeSource;
    }

    private HashMap<String, TransactionAttribute> getRuleBasedTxAttributeMap() {
        HashMap<String, TransactionAttribute> txMethods = new HashMap<>();

        RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
        txAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        txMethods.put("*", txAttribute);

        return txMethods;
    }

    // -------------------------------------------------------------
    // TransactionAdvisor 설정
    // -------------------------------------------------------------

    @Bean
    public Advisor txAdvisor(DataSourceTransactionManager txManager) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* cis..*Impl.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice(txManager));
    }
}
