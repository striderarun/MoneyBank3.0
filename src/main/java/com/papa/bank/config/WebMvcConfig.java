package com.papa.bank.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@EnableTransactionManagement
@Configuration
/**@ImportResource("classpath:datasource-context.xml")**/
@ComponentScan(basePackages = { "com.papa.bank" })
class WebMvcConfig extends WebMvcConfigurerAdapter {

	DriverManagerDataSource dataSource = null;

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

	@Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }

	@Bean
	public DataSource dataSource() {
		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:hsql://localhost/");
		dataSource.setUsername("SA");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setPackagesToScan("com.papa.bank");
		sessionFactory.setHibernateProperties(hibernateProperties);
		sessionFactory.setDataSource(dataSource());

		/** this approach doesnt work**/
		/**LocalSessionFactoryBuilder sessionFactoryBuiler = new LocalSessionFactoryBuilder(dataSource());
		sessionFactoryBuiler.scanPackages("com.papa.bank");
		sessionFactoryBuiler.setProperties(hibernateProperties);
		LocalSessionFactoryBean sessionFactory = (LocalSessionFactoryBean)sessionFactoryBuiler.buildSessionFactory(); **/

		return sessionFactory;
	}

	@Bean
	@Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
      HibernateTransactionManager txManager = new HibernateTransactionManager();
      txManager.setSessionFactory(sessionFactory);

      return txManager;
    }

	@Bean
    public DozerBeanMapper dozerMapper() {
		DozerBeanMapper mapper = new DozerBeanMapper();

		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {

			@Override
			protected void configure() {
				// TODO Auto-generated method stub

			}
		};
		/** Set Dozer Mapping xml Version 1
		List<String> dozerString = new ArrayList<String>();
		dozerString.add("dozer-bean-mappings.xml");
		mapper.setMappingFiles(dozerString);
		**/
		return mapper;
    }

}