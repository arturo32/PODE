package br.ufrn.imd.pode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Classe de configuração de segurança da aplicação
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private CustomUserDetailsService userDetailsService;

	public CustomUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	@Autowired
	public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
				.antMatchers("/v2/**").permitAll()
				.antMatchers("/swagger-ui.html").permitAll()
				.antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/css/**", "/js/**", "/fonts/**", "/fragmentos/**", "/imagens/**").permitAll()

				// Routes Security
				// TODO Segurança das rotas
				.mvcMatchers(HttpMethod.GET,"/api/estudante").hasAnyRole("ADMIN")

				.mvcMatchers(HttpMethod.GET,"/api/disciplina").authenticated()

				// others
				.anyRequest().permitAll()
				.and().formLogin().loginProcessingUrl("/api/login")
				.and().httpBasic().disable();

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}
}