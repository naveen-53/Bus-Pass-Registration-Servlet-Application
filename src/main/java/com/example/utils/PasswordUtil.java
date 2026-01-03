package com.example.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.servlet.BusPassServlet;

public class PasswordUtil {
	private static final Logger LOG = LoggerFactory.getLogger(PasswordUtil.class);

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verify(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
