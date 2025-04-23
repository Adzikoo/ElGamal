package org.example.elgamal;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ElGamal {

    public static class PublicKey {
        public final BigInteger p, g, h;

        public PublicKey(BigInteger p, BigInteger g, BigInteger h) {
            this.p = p;
            this.g = g;
            this.h = h;
        }
    }

    public static class PrivateKey {
        public final BigInteger p, a;

        public PrivateKey(BigInteger p, BigInteger a) {
            this.p = p;
            this.a = a;
        }
    }


    public static class KeyPair {
        public final PublicKey publicKey;
        public final PrivateKey privateKey;

        public KeyPair(PublicKey pub, PrivateKey priv) {
            this.publicKey = pub;
            this.privateKey = priv;
        }
    }

    public static class CipherText {
        public final BigInteger c1, c2;

        public CipherText(BigInteger c1, BigInteger c2) {
            this.c1 = c1;
            this.c2 = c2;
        }

        @Override
        public String toString() {
            return c1.toString() + "," + c2.toString();
        }

        public static CipherText fromString(String s) {
            String[] parts = s.split(",");
            return new CipherText(new BigInteger(parts[0]), new BigInteger(parts[1]));
        }
    }

    // Generowanie kluczy
    public static KeyPair generateKeys(int bitLength) {
        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        BigInteger g = new BigInteger(bitLength - 1, random).mod(p.subtract(BigInteger.ONE)).add(BigInteger.TWO);
        BigInteger a = new BigInteger(bitLength - 2, random).mod(p.subtract(BigInteger.TWO)).add(BigInteger.TWO);
        BigInteger h = g.modPow(a, p);

        return new KeyPair(
                new PublicKey(p, g, h),
                new PrivateKey(p, a)
        );

    }

    // Szyfrowanie
    public static CipherText encrypt(BigInteger message, PublicKey pub) {
        SecureRandom random = new SecureRandom();
        BigInteger r = new BigInteger(pub.p.bitLength() - 1, random).mod(pub.p.subtract(BigInteger.ONE)).add(BigInteger.ONE);
        BigInteger c1 = pub.g.modPow(r, pub.p);
        BigInteger s = pub.h.modPow(r, pub.p);
        BigInteger c2 = message.multiply(s).mod(pub.p);
        return new CipherText(c1, c2);
    }

    // Deszyfrowanie
    public static BigInteger decrypt(CipherText cipher, PrivateKey priv) {
        BigInteger s = cipher.c1.modPow(priv.a, priv.p);
        BigInteger sInv = s.modInverse(priv.p);
        return cipher.c2.multiply(sInv).mod(priv.p);
    }

}
