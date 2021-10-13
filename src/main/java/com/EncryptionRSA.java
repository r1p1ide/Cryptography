package com;

import java.util.*;

/**
 * EncryptionRSA is a public-key cryptosystem that is widely used for
 * secure data transmission. In a public-key cryptosystem, the encryption
 * key is public and distinct from the decryption key, which is kept secret
 * (private). An RSA user creates and publishes a public key based on two
 * large prime numbers, along with an auxiliary value. The prime numbers are
 * kept secret. Messages can be encrypted by anyone, via the public key, but
 * can only be decoded by someone who knows the prime numbers.
 *
 * <p>In this program, the user must select the module N, which in turn is the product
 * of two primes P and Q. Next, the Euler function (p - 1) (q - 1) is calculated and
 * the open secret exponent is selected to form the public and private encryption keys.
 * Then the message is encrypted and decrypted using the RSA encryption algorithm
 *
 * @author Matveev Alexander
 * @version 1.0
 */
public class EncryptionRSA {

    /**
     * The alphabet used. Symbols of the English and Russian alphabets
     * have a different case for clarity and to eliminate confusion between
     * visually similar symbols of different alphabets.
     */
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZабвгдежзийклмнопрстуфхцчшщъыьэюя ,.:!?";

    /**
     * Prime number p.
     */
    private long p;

    /**
     * Prime number q.
     */
    private long q;

    /**
     * The product of p and q. The field is static for further use in the Factor class.
     */
    private static long n;

    /**
     * Calculation of the Euler function.
     */
    private long z = eulerFunction(p, q);

    /**
     * Selection of an public exponent.
     */
    private long e = publicKeyExponent();

    /**
     * Selection of an private exponent.
     */
    private long d = privateKeyExponent();

    /**
     * Creating a HashMap to form the character / ordinal ratio in the alphabet.
     */
    private final HashMap<Character, Integer> map;

    /**
     * Constructor of the EncryptionRSA class, inside which for each
     * value of the alphabet character is supplied the corresponding
     * key (ordinal number of the character).
     */
    public EncryptionRSA() {
        char[] charArray = ALPHABET.toCharArray();
        HashMap<Character, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < charArray.length; i++) {
            hashMap.put(charArray[i],i);
        }

        this.map = hashMap;
    }

    /**
     * Returns cipher text.
     *
     * <p> Basic encryption method based on binary exponentiation
     * for dealing with huge numbers.
     *
     * @param publicText the public text
     * @return the private (cipher) text
     */
    public String encryptMsg(String publicText) {
        char[] publicMessage = publicText.toCharArray();
        long[] buf = new long[publicMessage.length];
        for(int i = 0; i < publicMessage.length; i++){
            buf[i] = binPow(map.get(publicMessage[i]), e) % n;
        }
        return concatenateLongArray(buf);
    }

    /**
     * Returns public (decrypted) text.
     *
     * <p>Basic decryption method based on binary exponentiation
     * for dealing with huge numbers.
     *
     * @param cipherText the private (cipher) text
     * @return the public (decrypted) text
     */
    public String decryptMsg(String cipherText){
        long[] cipherMessage = splitStringToLongArray(cipherText);
        char[] buf = new char[cipherMessage.length];
        for (int i = 0; i < cipherMessage.length; i++) {
            cipherMessage[i] = binPow(cipherMessage[i], d) % n;
            buf[i] = ALPHABET.toCharArray()[(int) cipherMessage[i]];
        }
        return concatenateCharArray(buf);
    }

    /**
     * Fast exponentiation algorithm that allows to work with huge numbers.
     *
     * @param number the number to be raised to a power
     * @param power the power
     * @return the result of exponentiation
     */
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

    /**
     * Calculation of the Euler function.
     *
     * @param p the prime number p
     * @param q the prime number q
     * @return the Euler Function
     */
    public long eulerFunction(long p, long q) {
        return (p - 1) * (q - 1);
    }

    /**
     * Calculation of the public exponent e.
     *
     * <p> The algorithm is based on the calculation of the grand central dispatch.
     *
     * @return the public exponent
     */
    public long publicKeyExponent() {
        for (e = 2; e < z; e++) {
            if (grandCentralDispatch(e, z) == 1) {
                break;
            }
        }
        return e;
    }

    /**
     * Calculation of the private (secret) exponent e.
     *
     * <p> The algorithm is based on the calculation using the extended Euclid algorithm.
     *
     * @return the private (secret) exponent
     */
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

    /**
     * Calculating the grand central dispatch.
     *
     * @param e the number
     * @param z the number
     * @return the grand central dispatch
     */
    public long grandCentralDispatch(long e, long z) {
        if(e == 0){
            return z;
        }
        else{
            return grandCentralDispatch(z % e, e);
        }
    }

    /**
     * A method that writes long[] values to String.
     *
     * @param longArray the input long[]
     * @return the String
     */
    public String concatenateLongArray(long[] longArray) {
        StringBuilder sb = new StringBuilder();
        for (long val : longArray) {
            sb.append(val).append(" ");
        }
        return sb.toString();
    }

    /**
     * A method that writes char[] values to String.
     *
     * @param charArray the input char[]
     * @return the String
     */
    public String concatenateCharArray(char[] charArray) {
        StringBuilder sb = new StringBuilder();
        for (char val : charArray) {
            sb.append(val);
        }
        return sb.toString();
    }

    /**
     * A method that writes String values to long[].
     *
     * @param string the input string
     * @return the long[]
     */
    public long[] splitStringToLongArray(String string) {
        Scanner scanner = new Scanner(string);
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        long[] intArray = list.stream().mapToLong(i -> i).toArray();
        scanner.close();
        return intArray;
    }

    /**
     * Declaring two primes p and q.
     */
    public static class Factor {
        private final long p;
        private final long q;

        /**
         * A constructor of the Factor class that checks the input
         * module N for simplicity and throws an IllegalArgumentException
         * if the condition does not pass.
         *
         * <p>Decomposes the input module N into two prime p and q.
         *
         * @param n the module N
         */
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

        /**
         * Checks if the number is the prime.
         *
         * @param number the number
         * @return true if the number is prime otherwise false
         */
        public boolean isPrime(long number) {
            for (long i = 2; i * i <= number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Driver code.
     */
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
