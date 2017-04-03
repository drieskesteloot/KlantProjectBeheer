package be.mobyus.omj.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
                .antMatchers("/", "/public/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .failureUrl("/error.html")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .rememberMe()
				.and()
		        .authorizeRequests().antMatchers("/console/**").permitAll();
		
		http.csrf().disable();
        http.headers().frameOptions().disable();
    }
	
}