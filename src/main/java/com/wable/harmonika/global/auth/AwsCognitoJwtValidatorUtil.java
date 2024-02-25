package com.wable.harmonika.global.auth;

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
import com.wable.harmonika.global.error.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

public class AwsCognitoJwtValidatorUtil {


    private AwsCognitoJwtValidatorUtil() {
    }
    public static JWTClaimsSet validateAWSJwtToken(String token) throws ParseException, JOSEException, BadJOSEException, MalformedURLException, UnauthorizedException {
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

    public static void main(String[] args) throws MalformedURLException, BadJOSEException, UnauthorizedException, ParseException, JOSEException {

        String token = "eyJraWQiOiIrdFFsSkJ2cnR1SndsTXhIUUVFmYzdoeFwvemhJanNHTzZ2VkE9IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiI0MjhjZjAwMS01NDZlLTQ3MmYtODRhYi1iNGQwZTU0OGE1MGQiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYWRkcmVzcymLTg0YWItYjRkMGU1NDhhNTBkIiwiYXVkIjoiM2J2b25nb2hiY2ZpMTZmNW8zNHE3MnVkMXYiLCJldmVudF9pZCI6IjhkMDBjNmVkLTJkMWQtMTFlOC05ZDgxLTI3MDZlZTM1NjVlYyIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTIxNjQ2NTU4LCJuYW1lIjoiVFFDRkQgVXNlciIsInBob25lX251bWJlciI6Iis5MTk4OTAxMDUyNjIiLCJleHAiOjE1MjE2NTAxNTgsImlhdCI6MTUyMTY0NjU1OCwiZW1haWwiOiJhYmNAeHl6LmNvbSJ9.JqCsBAO03PTgD_dgWwXPdUn4xlR3HEKhSosIyZpRLNchPTgRSjI-vEY4DQJj4-JgyU2SdQXuAEaG8yQPMh4jTQX3iaf2vr-qEXy7Iy5pPVCIFt0Vi8YrMO2IzAbaReGqWu6bSjSfTt_VE-ZfPT5RTuWPuvG2mbeltHOirrreuZ652T-RbW6g7o-3QnJx0U887T_XLlqELdu8dNo5Cgmsbreu4KOnUCf6FG6LVOgI-mnewDqESYa07hn4NrnDG7LHtlmmFvonLn9Xq1p9xcw";

       /* JsonObject payload = getPayload(token);
        JsonObject header = getHeader(token);
        String  signature = getSignature(token);*/

        JWTClaimsSet jwtClaimsSet = AwsCognitoJwtValidatorUtil.validateAWSJwtToken(token);
        System.out.println(jwtClaimsSet.toJSONObject());
    }

}