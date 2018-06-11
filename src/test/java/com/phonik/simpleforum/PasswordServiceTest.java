package com.phonik.simpleforum;

import org.jasypt.digest.StandardStringDigester;
import org.jasypt.util.digest.Digester;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordServiceTest {


    @Test
    public void userSignUp() {
        StandardStringDigester stringDigest = new StandardStringDigester();
        stringDigest.setSaltSizeBytes(4);
        stringDigest.setAlgorithm("SHA-1");
        stringDigest.setIterations(5000);
        String pass = "verySecret";
        String digest = stringDigest.digest(pass);
        boolean matches = stringDigest.matches(pass, digest);
        System.out.println(matches);
    }

    @Test
    public void strongEncryptorTest() {
        StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
        String pass = "verySecret";
        String digest = spe.encryptPassword(pass);
        assertNotEquals(pass, digest);
        boolean b = spe.checkPassword(pass, digest);
        assertTrue(b);

    }

}