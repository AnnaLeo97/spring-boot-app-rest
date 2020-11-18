package springbootapp.config;

import springbootapp.config.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan("springbootapp")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.formLogin()
//                // указываем страницу с формой логина
//                //указываем логику обработки при логине
//                .successHandler(new LoginSuccessHandler())
//                // указываем action с формы логина
//                //.loginProcessingUrl("/login")
//                // Указываем параметры логина и пароля с формы логина
//                .usernameParameter("j_username")
//                .passwordParameter("j_password")
//                // даем доступ к форме логина всем
//                .permitAll();
//
//        http.logout()
//                // разрешаем делать логаут всем
//                .permitAll()
//                // указываем URL логаута
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                // указываем URL при удачном логауте
//                .logoutSuccessUrl("/login")
//                //выкюлчаем кроссдоменную секьюрность (на этапе обучения неважна)
//                .and().csrf().disable();
//
//        http
//                // делаем страницу регистрации недоступной для авторизированных пользователей
//                .authorizeRequests()
//                //страницы аутентификаци доступна всем
//                .antMatchers("/login").anonymous()
//                // защищенные URL
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .anyRequest().authenticated();
//    }

        http.formLogin()
                .loginPage("/")
                .successHandler(new LoginSuccessHandler())
                .loginProcessingUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll();

        http.logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout")
                .and().csrf().disable();

        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/login").anonymous();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}