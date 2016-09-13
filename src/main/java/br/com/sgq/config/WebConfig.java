package br.com.sgq.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.sgq.scope.spring.ViewScope;

@Configuration 
@EnableWebMvc 
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public static CustomScopeConfigurer customScopeConfigurer(){
		CustomScopeConfigurer view = new CustomScopeConfigurer();
		Map<String,Object> scopes = new HashMap<String,Object>();
		scopes.put("view",new ViewScope());
		view.setScopes(scopes);
		return view;
	}
}
