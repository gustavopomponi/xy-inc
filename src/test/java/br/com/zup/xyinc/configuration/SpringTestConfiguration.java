package br.com.zup.xyinc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.zup.xyinc.services.PoiService;
import br.com.zup.xyinc.services.impl.PoiServiceImpl;

@Configuration
public class SpringTestConfiguration {
	
	@Bean
	public PoiService transactionService() {
	    return new PoiServiceImpl();
	}

}
