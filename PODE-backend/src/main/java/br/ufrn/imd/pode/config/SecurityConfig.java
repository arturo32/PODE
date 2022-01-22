package br.ufrn.imd.pode.config;

import br.ufrn.imd.pode.modelo.dto.AuthDto;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Classe de configuração de segurança da aplicação
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private DetalhesUsuarioServico userDetailsService;

	public DetalhesUsuarioServico getUserDetailsService() {
		return userDetailsService;
	}

	@Autowired
	public void setUserDetailsService(DetalhesUsuarioServico userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestCache().disable() // do not preserve original request before redirecting to login page as we will
										// return status code instead of redirect to login page (this is important to
										// disable otherwise session will be created on every request (not containing
										// sessionId/authToken) to non existing endpoint aka curl -i -X GET
										// 'http://localhost:8080/unknown')
				.authorizeRequests().antMatchers("/h2-console/**").permitAll().antMatchers("/v2/**").permitAll()
				.antMatchers("/swagger-ui.html").permitAll().antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/css/**", "/js/**", "/fonts/**", "/fragmentos/**", "/imagens/**").permitAll()

				// Routes Security
				// TODO Segurança das rotas
//				.mvcMatchers(HttpMethod.POST,"/api/estudante").permitAll()

//				.mvcMatchers(HttpMethod.GET,"/api/disciplina").authenticated()

				// others
				.anyRequest().permitAll().and().formLogin().loginProcessingUrl("/login")
				.successHandler(this::onAuthenticationSuccess) // success authentication
				.failureHandler((req, resp, ex) -> resp.setStatus(HttpServletResponse.SC_FORBIDDEN)).and() // bad
																											// credentials
				.sessionManagement()
				.invalidSessionStrategy((req, resp) -> resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED)).and() // if
																													// user
																													// provided
																													// expired
																													// session
																													// id
				.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()); // return status code on
																								// logout

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
		AuthDto dto = (AuthDto) auth.getPrincipal();
		ObjectMapper mapper = new ObjectMapper();
		response.getOutputStream().print(mapper.writeValueAsString(dto));
	}
}