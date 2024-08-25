package mattmck.mywebservice;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atomikos.jdbc.AtomikosDataSourceBean;

@Configuration
@EnableJpaRepositories(basePackages = {"mattmck.mywebservice.persistence.oradb"},
	//transactionManagerRef = "oraDbTransactionManager",	
	entityManagerFactoryRef = "oraDbEntityManagerFactory")
@EnableTransactionManagement
public class OraDbXaRepositoryConfig {

	
//	grant select on sys.dba_pending_transactions to <user name>;
//	grant select on sys.pending_trans$ to <user name>;
//	grant select on sys.dba_2pc_pending to <user name>;
//	grant execute on sys.dbms_system to <user name>; //up to driver version 10.2.0.3 
//	grant execute on dbms_xa to <user name>; //driver version 10.2.0.4 or higher	

	@Bean(name = "oraDbDataSourceProperties")
    @ConfigurationProperties("spring.datasource.oradb")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

	@Bean(name = "oraDbDataSource")
    public DataSource dataSource(@Qualifier("oraDbDataSourceProperties") DataSourceProperties properties) {

    	Properties xaProperties = new Properties();
    	xaProperties.setProperty ("user", properties.getUsername());
    	xaProperties.setProperty ("password", properties.getPassword());
    	xaProperties.setProperty ("URL", properties.getUrl());

    	AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
    	xaDataSource.setUniqueResourceName("xa_oracle");
    	xaDataSource.setXaDataSourceClassName("oracle.jdbc.xa.client.OracleXADataSource");
    	xaDataSource.setPoolSize(10);
    	xaDataSource.setXaProperties(xaProperties);
    	
    	return xaDataSource;
    }
    
	@Bean(name = "oraDbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      @Qualifier("oraDbDataSource") DataSource dataSource,
      EntityManagerFactoryBuilder builder) {
        return builder
          .dataSource(dataSource)
          .packages("mattmck.mywebservice.persistence.oradb")
          .persistenceUnit("oraDbPersistenceUnit")
          .jta(true)
          .build();
    }
}
