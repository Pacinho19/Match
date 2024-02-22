package pl.pacinho.match.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("1")
                .password(getPasswordEncoder().encode("1"))
                .roles("player_role")
                .and()
                .withUser("2")
                .password(getPasswordEncoder().encode("2"))
                .roles("player_role")
                .and()
                .withUser("3")
                .password(getPasswordEncoder().encode("3"))
                .roles("player_role")
                .and()
                .withUser("4")
                .password(getPasswordEncoder().encode("4"))
                .roles("player_role");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/match").permitAll()
                .antMatchers("/match/games").permitAll()
                .antMatchers("/match/game/board/generate/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }
}
