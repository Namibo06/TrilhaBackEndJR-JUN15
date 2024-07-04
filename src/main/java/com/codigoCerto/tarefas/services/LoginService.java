package com.codigoCerto.tarefas.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.codigoCerto.tarefas.dtos.LoginDTO;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginService {
    public String createToken(){
        try{
            Date now = new Date();
            Date expirationDate = new Date(now.getTime() + 3600 *  1000);
            Algorithm algorithm = Algorithm.HMAC256("CodigoCertoTrilhaBackEndJr");

            return JWT.create()
                    .withIssuer("Código Certo")
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException("Falha ao criar token");
        }
    }

    public void verifyToken(String token){
        DecodedJWT decodedJWT;
        try{
            Algorithm algorithm = Algorithm.HMAC256("CodigoCertoTrilhaBackEndJr");
            JWTVerifier verifier=JWT.require(algorithm)
                    .withIssuer("Codigo Certo")
                    .build();

            decodedJWT = verifier.verify(token);
        }catch (JWTVerificationException e){
            throw new JWTVerificationException("Falha ao autenticar token: ",e);
        }
    }
}
