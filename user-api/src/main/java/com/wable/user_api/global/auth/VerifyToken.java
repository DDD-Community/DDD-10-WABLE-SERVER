package com.wable.user_api.global.auth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.wable.user_api.domain.user.entity.Users;
import com.wable.user_api.global.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
@Component
@RequiredArgsConstructor
public class VerifyToken {

    @Value("${cognito.clientId}")
    private String clientId;

    @Value("${cognito.issuerUri}")
    private String issuerUri;

    public boolean verify(String IDToken) throws ParseException, MalformedURLException, BadJOSEException, JOSEException {
        // decode ID Token
        JWTClaimsSet jwtSet = new AwsCognitoJwtValidatorUtil().validateAWSJwtToken(IDToken);

        // check exp in claim
        Object exp = jwtSet.getClaim("exp");
        Date expDate = (Date) exp;
        System.out.println("expDate = " + expDate + " \n");
        if (expDate.before(Date.from(Instant.now()))) {

        }
        //aud in ID token, client_id in access token = client_id user pool
        Object aud = jwtSet.getClaim("aud");
        ArrayList audArrayList = (ArrayList) aud;
        for (int i = 0; i < audArrayList.size(); i++) {
            System.out.print("aud" + audArrayList.get(i) + " \n");
        }
        if (!audArrayList.contains(clientId)) {
        }
        // issuer (iss) = https://cognito-idp.ap-northeast-2.amazonaws.com/ap-northeast-2_80Se4Ok5g
        String iss = jwtSet.getStringClaim("iss");
        System.out.println("iss =" + iss + " \n");
        if (!iss.equals(issuerUri)) {
        }
        return true;
    }
}
