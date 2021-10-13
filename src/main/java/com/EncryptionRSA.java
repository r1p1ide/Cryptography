package com;

import java.util.*;

public class EncryptionRSA {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZабвгдежзийклмнопрстуфхцчшщъыьэюя ,.:!?";
    private long p;
    private long q;
    private static long n;
    private long z = eulerFunction(p, q);
    private long e = publicKeyExponent();
    private long d = privateKeyExponent();

    private final HashMap<Character, Integer> map;

    public EncryptionRSA() {
        char[] charArray = ALPHABET.toCharArray();
        HashMap<Character, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < charArray.length; i++) {
            hashMap.put(charArray[i],i);
        }

        this.map = hashMap;
    }

    public String encryptMsg(String publicText) {
        char[] publicMessage = publicText.toCharArray();
        long[] buf = new long[publicMessage.length];
        for(int i = 0; i < publicMessage.length; i++){
            buf[i] = binPow(map.get(publicMessage[i]), e) % n;
        }
        return concatenateLongArray(buf);
    }

    public String decryptMsg(String cipherText){
        long[] cipherMessage = splitStringToIntArray(cipherText);
        char[] buf = new char[cipherMessage.length];
        for (int i = 0; i < cipherMessage.length; i++) {
            cipherMessage[i] = binPow(cipherMessage[i], d) % n;
            buf[i] = ALPHABET.toCharArray()[(int) cipherMessage[i]];
        }
        return concatenateCharArray(buf);
    }

    public long binPow(long number, long power) {
        long result = 1;
        while (power > 0) {
            if (power % 2 != 0){
                result = result * number % n;
            }
            number = number * number % n;
            power /= 2;
        }
        return result;
    }

    public long eulerFunction(long p, long q) {
        return (p - 1) * (q - 1);
    }

    public long publicKeyExponent() {
        for (e = 2; e < z; e++) {
            if (grandCentralDispatch(e, z) == 1) {
                break;
            }
        }
        return e;
    }

    public long privateKeyExponent() {
        for(long i = 0; i <= 9; i++){
            long x = 1 + (i * z);
            if(x % e == 0){
                d = x/e;
                break;
            }
        }
        return d;
    }

    public static long grandCentralDispatch(long e, long z) {
        if(e == 0){
            return z;
        }
        else{
            return grandCentralDispatch(z % e, e);
        }
    }

    public String concatenateLongArray(long[] longArray) {
        StringBuilder sb = new StringBuilder();
        for (long val : longArray) {
            sb.append(val).append(" ");
        }
        return sb.toString();
    }

    public String concatenateCharArray(char[] charArray) {
        StringBuilder sb = new StringBuilder();
        for (char val : charArray) {
            sb.append(val);
        }
        return sb.toString();
    }
    
    public long[] splitStringToIntArray(String string) {
        Scanner scanner = new Scanner(string);
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        long[] intArray = list.stream().mapToLong(i -> i).toArray();
        scanner.close();
        return intArray;
    }

    static class Factor {
        private final long p;
        private final long q;

        public Factor(long n) {
            for (long i = 2; i * i <= n; i++) {
                if (n % i == 0) {
                    if (!isPrime(n / i)){
                        throw new IllegalArgumentException("Too much multipliers");
                    }
                    this.p = n / i;
                    this.q = i;
                    return;
                }
            }
            throw new IllegalArgumentException("Prime number");
        }

        public boolean isPrime(long number) {
            for (long i = 2; i * i <= number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        EncryptionRSA encryptionRSA = new EncryptionRSA();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите n: ");
        n = scanner.nextLong();
        Factor factor;
        try {
            factor = new Factor(n);
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }

        encryptionRSA.p = factor.p;
        encryptionRSA.q = factor.q;

        System.out.println("p: " + factor.p);
        System.out.println("q: " + factor.q);


        encryptionRSA.z = encryptionRSA.eulerFunction(encryptionRSA.p, encryptionRSA.q);
        System.out.println("Функция эйлера: " + encryptionRSA.z);

        encryptionRSA.e = encryptionRSA.publicKeyExponent();
        System.out.println("Открытая экспонента: " + encryptionRSA.e);

        encryptionRSA.d = encryptionRSA.privateKeyExponent();
        System.out.println("Секретная экспонента: " + encryptionRSA.d);

        String text1 = "I BELIEVE I CAN FLY I BELIEVE I CAN TOUCH THE SKY";
        System.out.println("Шифртекст: " + encryptionRSA.encryptMsg(text1));

        String text2 = "512 195112 1 64 1331 512 64 9261 64 195112 512 195112 8 0 2197 195112 125 1331 13824 195112 512 195112 1 64 1331 512 64 9261 64 195112 512 195112 8 0 2197 195112 6859 2744 8000 8 343 195112 6859 343 64 195112 5832 1000 13824 ";
        System.out.println("Дешифрованный текст: " + encryptionRSA.decryptMsg(text2));
    }
}
