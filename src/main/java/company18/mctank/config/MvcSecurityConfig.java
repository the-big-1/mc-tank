package company18.mctank.config;

import org.salespointframework.SalespointSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
class MvcSecurityConfig extends SalespointSecurityConfiguration {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**").permitAll().and()
				.formLogin().loginProcessingUrl("/login").and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}
}