package com.nagarro.employee_portal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("employeeService")
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable()
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**", "/h2/**","/login.html","/signup","/","/actuator/**")
                .and()
                .cors().disable();
        http
                .csrf()
                .disable()
                .authorizeRequests()
//                .antMatchers("/*.html", "/h2/**", "/post-user","/employee/add/**","/**").permitAll()
                .antMatchers("/*","/index.html","/index","/login","/login.html","/signup","/signup.html","/actuator/**").permitAll()
                .anyRequest().authenticated().and()
                .formLogin()
                .loginPage("/login.html")
                .failureUrl("/logout")
                .and()
                .logout()
                .logoutSuccessUrl("/login.html");
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
