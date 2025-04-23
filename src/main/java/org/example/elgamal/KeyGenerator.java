package org.example.elgamal;

import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyGenerator {

    private static final int BIT_LENGTH = 2048; // lub 1024, jeśli chcesz krótsze

    public static class KeyPair {
        public final BigInteger p;
        public final BigInteger g;
        public final BigInteger a; // klucz prywatny
        public final BigInteger h; // klucz publiczny

        public KeyPair(BigInteger p, BigInteger g, BigInteger a, BigInteger h) {
            this.p = p;
            this.g = g;
            this.a = a;
            this.h = h;
        }
    }

    public static KeyPair generateKeys() {
        SecureRandom random = new SecureRandom();

        // 1. Wygeneruj dużą liczbę pierwszą p
        BigInteger p = BigInteger.probablePrime(BIT_LENGTH, random);

        // 2. Wybierz g: 1 < g < p-1
        BigInteger g;
        do {
            g = new BigInteger(BIT_LENGTH - 1, random);
        } while (g.compareTo(BigInteger.ONE) <= 0 || g.compareTo(p.subtract(BigInteger.ONE)) >= 0);

        // 3. Wybierz losowe a: 1 < a < p-1
        BigInteger a;
        do {
            a = new BigInteger(BIT_LENGTH - 2, random);
        } while (a.compareTo(BigInteger.ONE) <= 0 || a.compareTo(p.subtract(BigInteger.ONE)) >= 0);

        // 4. Oblicz h = g^a mod p
        BigInteger h = g.modPow(a, p);

        return new KeyPair(p, g, a, h);
    }

    public static void main(String[] args) {
        KeyPair keyPair = generateKeys();

        System.out.println("p = " + keyPair.p.toString(16));
        System.out.println("g = " + keyPair.g.toString(16));
        System.out.println("a (private) = " + keyPair.a.toString(16));
        System.out.println("h (public) = " + keyPair.h.toString(16));
    }
}
