package com.nouhoun.springboot.jwt.integration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

import static com.nouhoun.springboot.jwt.integration.config.SecurityConfig.SIGNING_KEY;

/**
 * Created by nydiarra on 06/05/17.
 */
@Configuration
@EnableAuthorizationServer
@EnableResourceServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    static final String CLIEN_ID = "testjwtclientid";
    static final String GRANT_TYPE = "password";
    static final String SCOPE_READ = "read";
    static final String SCOPE_WRITE = "write";
    static final String RESOURCES_IDS = "testjwtresourceid";

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception
    {
      oauthServer.tokenKeyAccess("isAuthenticated()");
      oauthServer.checkTokenAccess("isAuthenticated()");    
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
      /*
      // 클라이언트 정보를 직접 기술 할 수 있다
      // @formatter:off
      clients.inMemory()
          .withClient("my_client_id") // 클라이언트 아이디
              .secret("my_client_secret") // 클라이언트 시크릿
              
              // 엑세스토큰 발급 가능한 인증 타입
              // 기본이 다섯개, 여기 속성이 없으면 인증 불가
              .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
              
              .authorities("ROLE_MY_CLIENT")  // 클라이언트에 부여된 권한
              
              // 이 클라이언트로 접근할 수 있는 범위 제한
              .scopes("read", "write")  // 해당 클라이언트로 API를 접근 했을때 접근 범위를 제한 시키는 속성
              .accessTokenValiditySeconds(60 * 60 * 4)  // 이 클라이언트로 발급된 엑세스토큰의 시간 (단위:초)
              .refreshTokenValiditySeconds(60 * 60 * 24 * 120)// 이 클라이언트로 발급된 리프러시토큰의 시간 (단위:초)
          .and()
          .withClient("your_client_id")
              .secret("your_client_secret")
              .authorizedGrantTypes("authorization_code", "implicit")
              .authorities("ROLE_YOUR_CLIENT")
              .scopes("read")
          .and();
      // @formatter:on
*/
      
      
        configurer.inMemory()
                .withClient(CLIEN_ID)
                .secret(SIGNING_KEY)
                .authorizedGrantTypes(GRANT_TYPE)
//                .scopes(SCOPE_READ, SCOPE_WRITE)
                .scopes(SCOPE_READ, SCOPE_WRITE)
//                .resourceIds(RESOURCES_IDS)
                
                // 클라이언트에 부여된 권한
                .authorities("ROLE_MY_CLIENT")
                // 이 클라이언트로 발급된 엑세스토큰의 시간 (단위:초)
                .accessTokenValiditySeconds(60 * 60 * 4)
                // 이 클라이언트로 발급된 리프러시토큰의 시간 (단위:초)
                .refreshTokenValiditySeconds(60 * 60 * 24 * 120)
                
                
                
                .and()
                
                
                
                .withClient("withClient_ID")
                .secret("SIGNING_KEY_PWD")
                
                
                //.authorizedGrantTypes("password", "client_credentials")
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                .scopes("read")
//                .resourceIds("resourceIds")
                
                // 클라이언트에 부여된 권한
                .authorities("ROLE_MY_CLIENT")
                // 이 클라이언트로 발급된 엑세스토큰의 시간 (단위:초)
                .accessTokenValiditySeconds(60 * 60 * 4)
                // 이 클라이언트로 발급된 리프러시토큰의 시간 (단위:초)
                .refreshTokenValiditySeconds(60 * 60 * 24 * 120)
                ;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        endpoints.tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .tokenEnhancer(enhancerChain)
                .authenticationManager(authenticationManager)
                ;
    }

}
