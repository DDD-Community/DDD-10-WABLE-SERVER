package com.wable.harmonika.global.auth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.global.error.exception.UnauthorizedException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import static com.wable.harmonika.global.auth.AwsCognitoJwtValidatorUtil.validateAWSJwtToken;
@Configuration
public class VerifyToken {

    @Value("${cognito.clientId}")
    private String clientId;

    @Value("${cognito.issuerUri}")
    private String issuerUri;

    public boolean verify(String IDToken) throws ParseException, MalformedURLException, BadJOSEException, JOSEException {
        // decode ID Token
        JWTClaimsSet jwtSet = validateAWSJwtToken(IDToken);

        // check exp in claim
        Object exp = jwtSet.getClaim("exp");
        Date expDate = (Date) exp;
        if (expDate.before(Date.from(Instant.now()))) {
            return false;
        }
        // aud in ID token, client_id in access token = client_id user pool
        Object aud = jwtSet.getClaim("aud");
        ArrayList audArrayList = (ArrayList) aud;
        if (!audArrayList.contains(clientId)) {
            return false;
        }
        // issuer (iss) = https://cognito-idp.ap-northeast-2.amazonaws.com/ap-northeast-2_80Se4Ok5g
        String iss = jwtSet.getStringClaim("iss");
        System.out.println("iss = " + iss);
        if (!iss.equals(issuerUri)) {
            return false;
        }
        return true;
    }
}
