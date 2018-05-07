package com.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * spring事务代码分析：
 * 	@EnableTransactionManagement(AdviceMode mode() default AdviceMode.PROXY;) 导入了
 * 		@Import(TransactionManagementConfigurationSelector.class)
 * 			extends AdviceModeImportSelector<EnableTransactionManagement>
 * 			 	implements ImportSelector 导入AutoProxyRegistrar.class和ProxyTransactionManagementConfiguration.class
 * 	AutoProxyRegistrar.class分析：
 * 		AutoProxyRegistrar implements ImportBeanDefinitionRegistrar //导入组件
 * 			通过其方法注册一个InfrastructureAdvisorAutoProxyCreator.class的RootBeanDefinition
 * 			InfrastructureAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator
 * 				extends AbstractAutoProxyCreator
 *					extends ProxyProcessorSupport implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *					//与AOP类似，先利用后置处理器机制在对象创建之后包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用
 * 	ProxyTransactionManagementConfiguration.class分析
 * 		@Configuration 是一个spring配置类
 * 		 extends AbstractTransactionManagementConfiguration
 * 		 它会注册事务增强器（其中包括
 * 		 	transactionAttributeSource，里面又有（parseTransactionAnnotation.parseTransactionAnnotation）用于解析@Transactional
 * 		 	 TransactionInterceptor extends TransactionAspectSupport implements MethodInterceptor, Serializable）
 *
 *
 *
 *
 */
@EnableTransactionManagement
@ComponentScan("com.tx")
@Configuration
public class AnnotationTXConfig {
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
