package mattmck.mywebservice;

import javax.sql.DataSource;

import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atomikos.jdbc.AtomikosDataSourceBean;

@Configuration
@EnableJpaRepositories(basePackages = {"mattmck.mywebservice.persistence.pgdb"},
	//transactionManagerRef = "pgDbTransactionManager",	
	entityManagerFactoryRef = "pgDbEntityManagerFactory")
@EnableTransactionManagement
public class PgDbXaRepositoryConfig {

	// examples
	// https://github.com/jejo/atomikos-spring-boot/blob/master/src/main/java/com/test/atomikos/config/Db1DataSourceConfig.java
	// https://github.com/kloia/atomikos-spring/blob/main/src/main/java/com/kloia/atomikos/configuration/CustomerDataSourceConfiguration.java
	
	//Caused by: org.postgresql.util.PSQLException: ERROR: prepared transactions are disabled
	//Hint: Set max_prepared_transactions to a nonzero value.
	//Run following Query e.g. ALTER SYSTEM SET max_prepared_transactions = 100;
	//Restart Service
	//Validate by running following Query :- SHOW max_prepared_transactions;
	
	@Bean(name = "pgDbDataSourceProperties")
    @ConfigurationProperties("spring.datasource.pgdb")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

	@Bean(name = "pgDbDataSource")
    @Primary
    public DataSource dataSource(@Qualifier("pgDbDataSourceProperties") DataSourceProperties properties) {

    	PGXADataSource dataSource = new PGXADataSource(); 
    	dataSource.setURL(properties.getUrl());
    	dataSource.setUser(properties.getUsername());
    	dataSource.setPassword(properties.getPassword());
    	
    	AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
    	xaDataSource.setXaDataSourceClassName("org.postgresql.Driver");
    	xaDataSource.setXaDataSource(dataSource);       
    	xaDataSource.setUniqueResourceName("xa_postgres");
    	xaDataSource.setPoolSize(10);
        return xaDataSource;
    }

    @Bean(name = "pgDbEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      @Qualifier("pgDbDataSource") DataSource dataSource,
      EntityManagerFactoryBuilder builder) {
    	
        return builder
          .dataSource(dataSource)
          .packages("mattmck.mywebservice.persistence.pgdb")
          .persistenceUnit("pgDbPersistenceUnit")
          .jta(true)
          .build();
    }
}
