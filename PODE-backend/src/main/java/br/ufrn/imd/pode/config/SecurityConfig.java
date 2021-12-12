package br.ufrn.imd.pode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;

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
		http.requestCache().disable() // do not preserve original request before redirecting to login page as we will return status code instead of redirect to login page (this is important to disable otherwise session will be created on every request (not containing sessionId/authToken) to non existing endpoint aka curl -i -X GET 'http://localhost:8080/unknown')
				.authorizeRequests().antMatchers("/h2-console/**").permitAll()
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
				.successHandler((req, resp, auth) -> resp.setStatus(HttpServletResponse.SC_OK)) // success authentication
				.failureHandler((req, resp, ex) -> resp.setStatus(HttpServletResponse.SC_FORBIDDEN)).and() // bad credentials
				.sessionManagement()
				.invalidSessionStrategy((req, resp) -> resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED)).and() // if user provided expired session id
				.logout()
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()); // return status code on logout


		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder
				.userDetailsService(userDetailsService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}
}