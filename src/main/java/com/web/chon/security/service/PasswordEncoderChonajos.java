package com.web.chon.security.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.springframework.security.crypto.password.PasswordEncoder;

@Component(value = "passwordEncoder")
public class PasswordEncoderChonajos implements PasswordEncoder {

    private final static Logger logger = LoggerFactory.getLogger(PasswordEncoderChonajos.class);

    @Override
    public String encode(CharSequence rawPassword) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        StringBuffer hexString = new StringBuffer();

        byte[] hash = md.digest(rawPassword.toString().getBytes());

        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0"
                        + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }

        return hexString.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        if (encodedPassword == null || encodedPassword.length() == 0) {
            logger.warn("Password encoded vacio");
            return false;
        }
        String encodeRawPassword = encode(rawPassword).toString().toUpperCase();
        System.out.println("rawPassword > encriptada matches: "+encodeRawPassword);
        boolean result = encodeRawPassword.equals(encodedPassword);

        if (!result) {
            logger.debug("Fallo comprobacion de seguridad");
            logger.debug("String encodedPasswordDeBase   {}", encodedPassword);
            logger.debug("String encodePasswordGenerado  {}", encodeRawPassword);
        }

        return result;
    }

}
