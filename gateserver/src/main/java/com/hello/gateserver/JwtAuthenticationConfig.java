package com.hello.gateserver;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * Config JWT.
 * Only one property 'shuaicj.security.jwt.secret' is mandatory.
 *
 * @author shuaicj 2017/10/18
 */
@Data
public class JwtAuthenticationConfig {


    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.expiration}")
    private int expiration; // default 24 hours

    @Value("${jwt.secret}")
    private String secret;
}
