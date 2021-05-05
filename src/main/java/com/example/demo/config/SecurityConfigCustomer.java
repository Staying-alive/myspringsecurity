package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class SecurityConfigCustomer extends WebSecurityConfigurerAdapter {

	@Autowired  
	private UserDetailsService userDetailsService;
	
	// zhurushujuyuan
	@Autowired
	private DataSource dataSource;
	
	// peizhicaocuoshujukuduixiang
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		//jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
		return jdbcTokenRepositoryImpl;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(password()); 
	}
	
	@Bean
	PasswordEncoder password() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// logout
;		http.logout().logoutUrl("/logout").permitAll();
		
		// meiyoufangwen1uanxian, tiaozhuanzidingyiyemian
		http.exceptionHandling().accessDeniedPage("/unauth.html");
								
		http.formLogin()  										// zidingyizijibianxiededengluyemian  
			.loginPage("/userLogin")					// denglufangwenyemian
			.loginProcessingUrl("/user/login")  				// denglufangwenlujing
			//.defaultSuccessUrl("/test/index").permitAll()		// dengluchenggongzhihou, tiaozhuanlujing
			.defaultSuccessUrl("/success.html").permitAll()		// dengluchenggongzhihou, tiaozhuanlujing
			.and().authorizeRequests()
				.antMatchers("/", "/test/hello", "/user/login").permitAll() // shezhinaxielujingkeyizhijiefangwen, buxuyaorenzheng
				// 1 .antMatchers("/test/index").hasAuthority("admins")		// dangqiandengluyonghu y, zhiyoujuyou admins quanxian, caikeyifangwen
				// 2 .antMatchers("/test/index").hasAnyAuthority("admins,manager")
				.antMatchers("/test/index").hasRole("sale")                 // jiyujuese, fangwenkongzhi
				// 4 hasanyrole
			.anyRequest().authenticated()
			// jizhuwozidongdenglukongzhi
			.and().rememberMe().tokenRepository(persistentTokenRepository())
								.tokenValiditySeconds(60) //shezhi youxiaoshichang
								.userDetailsService(userDetailsService);//shezhidetail
			//.and().csrf().disable();							// guanbi csrf fanghu
			//.and().csrf().ignoringAntMatchers("/proxy/**");
		
	}
}
