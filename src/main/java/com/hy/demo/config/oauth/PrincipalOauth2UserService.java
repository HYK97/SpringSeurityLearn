package com.hy.demo.config.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 구글로 부터 받은 userRequest 데이터를 후처리하는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        logger.info("userRequest = " + userRequest.getClientRegistration());
        logger.info("getAccessToken.getTokenValue() = " + userRequest.getAccessToken().getTokenValue());
        logger.info("loadUser(userRequest).getAttributes() = " + super.loadUser(userRequest).getAttributes());
        /**  loadUser(userRequest).getAttributes() 의 리턴값
         * {sub=12312423412341231412345123,
         * name=KsBi kim,
         * given_name=KsBi,
         * family_name=KsBi,
         * picture=https://lh3.google.com/a/AATXAJzPTCeqTDf3ywaH_28lKvL1=s96-c,
         * email=ddha963dw963@gmail.com,
         * email_verified=true,
         * locale=ko}
         *
         * 예를 들면 회원가입은
         * username = google_12312423412341231412345123 이런식으로 하면중복안됌
         * password = "암호화(겟인데어)"
         * email = ddha963dw963@gmail.com
         * role = ROLE_USER
         * provider = "google"
         * providerId= ="12312423412341231412345123" 이런식으로 저장
         * */

        return super.loadUser(userRequest);
    }
}
