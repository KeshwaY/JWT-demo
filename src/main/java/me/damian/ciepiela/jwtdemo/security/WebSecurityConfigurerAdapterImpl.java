package me.damian.ciepiela.jwtdemo.security;

import me.damian.ciepiela.jwtdemo.security.token.TokenCreator;
import me.damian.ciepiela.jwtdemo.security.token.filter.CustomAuthenticationFilter;
import me.damian.ciepiela.jwtdemo.security.token.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurerAdapterImpl extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final TokenCreator tokenCreator;

    public WebSecurityConfigurerAdapterImpl(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, TokenCreator tokenCreator) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.tokenCreator = tokenCreator;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String loginPath = "/api/v1/login";
        String refreshTokenPath = "/api/v1/token/refresh";
        String registerPath = "/api/v1/users/register";
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), tokenCreator);
        customAuthenticationFilter.setFilterProcessesUrl(loginPath);
        http
                .httpBasic()
                .and()
                .addFilterBefore(new CustomAuthorizationFilter(tokenCreator, loginPath, refreshTokenPath), UsernamePasswordAuthenticationFilter.class)
                .addFilter(customAuthenticationFilter)
                .authorizeRequests()
                .mvcMatchers(registerPath, loginPath, refreshTokenPath).permitAll()
                .mvcMatchers("/api/v1/users/**").hasAnyAuthority("OP_USER_MANAGEMENT")
                .mvcMatchers("/api/v1/roles/**").hasAnyAuthority("OP_ROLE_MANAGEMENT")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
