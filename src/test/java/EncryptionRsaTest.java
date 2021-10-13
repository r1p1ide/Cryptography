import com.EncryptionRSA;

import static testAssert.Assert.*;

public class EncryptionRsaTest {
    public static void main(String[] args) {
        testEulerFunction();
        testGrandCentralDispatch();
        testConcatenateLongArray();
        testConcatenateCharArray();
        testSplitStringToLongArray();
        testIsPrime();
        testIsPrimeException();
    }

    public static void testEulerFunction() {
        EncryptionRSA encryptionRSA = new EncryptionRSA();
        long actual = encryptionRSA.eulerFunction(389, 9677);
        assertEquals("testEulerFunction: ", 3754288, actual);
    }

    public static void testGrandCentralDispatch() {
        EncryptionRSA encryptionRSA = new EncryptionRSA();
        long actual = encryptionRSA.grandCentralDispatch(301500, 100500);
        assertEquals("testGrandCentralDispatch: ", 100500, actual);
    }

    public static void testConcatenateLongArray() {
        EncryptionRSA encryptionRSA = new EncryptionRSA();
        String actual = encryptionRSA.concatenateLongArray(new long[]{322, 228, 1488, 1337, 550, 282});
        assertEquals("testConcatenateLongArray: ", "322 228 1488 1337 550 282 ", actual);
    }

    public static void testConcatenateCharArray() {
        EncryptionRSA encryptionRSA = new EncryptionRSA();
        String actual = encryptionRSA.concatenateCharArray(new char[]{'.', 'J', 'х', '!', 'ф', 'г'});
        assertEquals("testConcatenateCharArray: ", ".Jх!фг", actual);
    }

    public static void testSplitStringToLongArray() {
        EncryptionRSA encryptionRSA = new EncryptionRSA();
        long[] actual = encryptionRSA.splitStringToLongArray("512 2 1094283 123213123");
        long[] expected = {512, 2, 1094283, 123213123};
        assertEquals("testSplitStringToLongArray: ", expected, actual);
    }

    public static void testIsPrime() {
        EncryptionRSA.Factor factor = new EncryptionRSA.Factor(3764353);
        boolean actual = factor.isPrime(9677);
        assertEquals("testIsPrime: ", true, actual);
    }

    public static void testIsPrimeException() {
        try {
            EncryptionRSA.Factor factor = new EncryptionRSA.Factor(3764354);
            boolean actual = factor.isPrime(9676);
            assertEquals("testIsPrimeException: ", false, actual);
            fail("Exception not found. Test failed.");
        }
        catch (Exception e) {
            assertEquals("testIsPrimeException: ", new IllegalArgumentException(), e);
        }
    }

}
