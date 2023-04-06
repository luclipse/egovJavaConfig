package org.egovframe.config.root;

import org.egovframe.cmm.util.EgovSampleExcepHndlr;
import org.egovframe.cmm.util.EgovSampleOthersExcepHndlr;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;
import org.egovframe.rte.fdl.cmmn.exception.handler.ExceptionHandler;
import org.egovframe.rte.fdl.cmmn.exception.manager.DefaultExceptionHandleManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableAspectJAutoProxy
public class ContextAspect {

    @Bean
    public AopExceptionTransfer aopExceptionTransfer(ExceptionTransfer exceptionTransfer){
        AopExceptionTransfer aopExceptionTransfer = new AopExceptionTransfer();
        aopExceptionTransfer.setExceptionTransfer(exceptionTransfer);
        return aopExceptionTransfer;
    }

    @Bean
    public ExceptionTransfer exceptionTransfer(@Qualifier("defaultExceptionHandleManager") DefaultExceptionHandleManager defaultExceptionHandleManager
            , @Qualifier("otherExceptionHandleManager") DefaultExceptionHandleManager otherExceptionHandleManager){
        ExceptionTransfer exceptionTransfer = new ExceptionTransfer();
        exceptionTransfer.setExceptionHandlerService(new DefaultExceptionHandleManager [] {defaultExceptionHandleManager, otherExceptionHandleManager});
        return exceptionTransfer;
    }

    @Bean
    public DefaultExceptionHandleManager defaultExceptionHandleManager(AntPathMatcher antPathMater, EgovSampleExcepHndlr egovHandler){
        DefaultExceptionHandleManager defaultExceptionHandleManager = new DefaultExceptionHandleManager();
        defaultExceptionHandleManager.setReqExpMatcher(antPathMater);
        defaultExceptionHandleManager.setPatterns(new String[]{"**service.impl.*"});
        defaultExceptionHandleManager.setHandlers(new ExceptionHandler[]{egovHandler});
        return defaultExceptionHandleManager;
    }

    @Bean
    public DefaultExceptionHandleManager otherExceptionHandleManager(AntPathMatcher antPathMater, EgovSampleOthersExcepHndlr otherHandler){
        DefaultExceptionHandleManager otherExceptionHandleManager = new DefaultExceptionHandleManager();
        otherExceptionHandleManager.setReqExpMatcher(antPathMater);
        otherExceptionHandleManager.setPatterns(new String[]{"**service.impl.*"});
        otherExceptionHandleManager.setHandlers(new ExceptionHandler[]{otherHandler});
        return otherExceptionHandleManager;
    }

    @Bean
    public EgovSampleExcepHndlr egovHandler(){
        EgovSampleExcepHndlr egovSampleExcepHndlr = new EgovSampleExcepHndlr();
        return egovSampleExcepHndlr;
    }

    @Bean
    public EgovSampleOthersExcepHndlr otherHandler() {
        EgovSampleOthersExcepHndlr egovSampleOthersExcepHndlr = new EgovSampleOthersExcepHndlr();
        return egovSampleOthersExcepHndlr;
    }

    public class AopExceptionTransfer {
        private ExceptionTransfer exceptionTransfer;

        public void setExceptionTransfer(ExceptionTransfer exceptionTransfer) {
            this.exceptionTransfer = exceptionTransfer;
        }

        @Pointcut("execution(* egovframework.example..impl.*Impl.*(..))")
        private void exceptionTransferService() {}

        @AfterThrowing(pointcut = "exceptionTransferService()", throwing="ex")
        public void doAfterThrowingExceptionTransferService(JoinPoint thisJoinPoint, Exception ex) throws Exception{
            exceptionTransfer.transfer(thisJoinPoint, ex);
        }
    }
}

