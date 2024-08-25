package mattmck.mywebservice;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.atomikos.jms.AtomikosConnectionFactoryBean;

@Configuration
public class JmsConfig {

	@Bean
    public ConnectionFactory connectionFactory() {
		
		ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory("tcp://localhost:61616");
        connectionFactory.setUserName("admin");
        connectionFactory.setPassword("admin");
        
		AtomikosConnectionFactoryBean xaConnectionFactory = new AtomikosConnectionFactoryBean(); 
		xaConnectionFactory.setUniqueResourceName("xa_activemq"); 
		xaConnectionFactory.setXaConnectionFactory(connectionFactory); 
		xaConnectionFactory.setPoolSize (5);
		
		return xaConnectionFactory;
    }
	
	@Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setDefaultDestinationName("testQueue1");
        return jmsTemplate;
    }
	
	@Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
