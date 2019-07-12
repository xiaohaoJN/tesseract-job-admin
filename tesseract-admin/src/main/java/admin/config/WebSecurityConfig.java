package admin.config;

import admin.security.TokenAuthenticationEntryPoint;
import admin.security.TokenAuthenticationFilter;
import admin.security.TokenLogoutHandler;
import admin.security.TokenLogoutSuccessHandler;
import admin.service.ITesseractUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static tesseract.core.constant.CommonConstant.HEARTBEAT_MAPPING_SUFFIX;
import static tesseract.core.constant.CommonConstant.REGISTRY_MAPPING_SUFFIX;

/**
 * @description: 安全配置类
 * @author: LeoLee
 * @company: ***
 * @version:
 * @date: 2019/7/9 14:03
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ITesseractUserService tesseractUserService;
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    @Autowired
    private TokenLogoutHandler tokenLogoutHandler;
    @Autowired
    private TokenLogoutSuccessHandler logoutSuccessHandler;

    /**
     * http安全配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 开启跨域共享
                .cors().and()
                // 跨域伪造请求限制.无效
                .csrf().disable()
                // 开启授权认证
                .authorizeRequests()
                // TODO 此处用来配置权限，或者使用注解配置权限
                .antMatchers("/tesseract-user/userList").hasAuthority("admin")
                .antMatchers("/tesseract-user/getUserAuthInfo").hasAnyAuthority("admin")
                .antMatchers("/tesseract-user/login").permitAll()
                .antMatchers("/tesseract-user/register").permitAll()
                .antMatchers("/tesseract-user/logout").permitAll()
                //内部心跳和注册放行
                .antMatchers("/tesseract-executor-detail" + HEARTBEAT_MAPPING_SUFFIX).permitAll()
                .antMatchers("/tesseract-executor" + REGISTRY_MAPPING_SUFFIX).permitAll()
                // 其它请求随意访问
                .anyRequest().authenticated().and()
                // 基于token，所以不需要session。无状态
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling()
                //自定义403返回
                .authenticationEntryPoint(new TokenAuthenticationEntryPoint()).and()
                .logout()
                .logoutUrl("/tesseract-user/logout")
                .addLogoutHandler(tokenLogoutHandler)
                .logoutSuccessHandler(logoutSuccessHandler).and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 密码生成策略.
     * 官方推荐使用BCrypt加密，并明确指出 sha 和 md5都是不安全的
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * cors跨越
     *
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        corsConfiguration.addExposedHeader("access-control-allow-methods");
        corsConfiguration.addExposedHeader("access-control-allow-headers");
        corsConfiguration.addExposedHeader("access-control-allow-origin");
        corsConfiguration.addExposedHeader("access-control-max-age");
        corsConfiguration.addExposedHeader("X-Frame-Options");

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/", corsConfiguration);
        return configurationSource;
    }
}
