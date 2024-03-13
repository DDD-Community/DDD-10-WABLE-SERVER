package com.wable.user_api.global.auth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.wable.user_api.global.error.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

public class AwsCognitoJwtValidatorUtil {

    public JWTClaimsSet validateAWSJwtToken(String token) throws ParseException, JOSEException, BadJOSEException, MalformedURLException, UnauthorizedException {
        try {
            String jsonWebKeyFileURL = AwsCognitoJwtParserUtil.getJsonWebKeyURL(token);

            ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
            JWKSource jwkSource = new RemoteJWKSet(new URL(jsonWebKeyFileURL));
            JWSAlgorithm jwsAlgorithm = JWSAlgorithm.RS256;
            JWSKeySelector keySelector = new JWSVerificationKeySelector(jwsAlgorithm, jwkSource);
            jwtProcessor.setJWSKeySelector(keySelector);
            JWTClaimsSet claimsSet = jwtProcessor.process(token, null);
            return claimsSet;
        }catch (BadJWTException e) {
            throw new UnauthorizedException(e.getLocalizedMessage());
        }

    }

}