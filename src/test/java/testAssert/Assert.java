package testAssert;

import java.util.Arrays;
import java.util.List;

public class Assert {

    public static void assertEquals(String testName, int expected, int actual) {
        if (expected == actual) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed: expected " + expected + ", actual " + actual);
        }
    }

    public static void assertEquals(String testName, long expected, long actual) {
        if (expected == actual) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed: expected " + expected + ", actual " + actual);
        }
    }

    public static void assertEquals(String testName, long[] expected, long[] actual) {
        if (Arrays.equals(expected, actual)) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed: expected " + Arrays.toString(expected) + ", actual " +
                    Arrays.toString(actual));
        }
    }

    public static void assertEquals(String testName, double expected, double actual) {
        if (expected == actual) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed: expected " + expected + ", actual " + actual);
        }
    }

    public static void assertEquals(String testName, boolean expected, boolean actual) {
        if (expected == actual) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed: expected " + expected + ", actual " + actual);
        }
    }

    public static void assertEquals(String testName, String expected, String actual) {
        if (expected.equals(actual)) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed: expected " + expected + ", actual " + actual);
        }
    }

    public static void assertEquals(String testName, char expected, char actual) {
        if (expected == actual) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed: expected " + expected + ", actual " + actual);
        }
    }

    public static void assertEquals(String testName, int[] expected, int[] actual) {
        if (Arrays.equals(expected, actual)) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed: expected " + Arrays.toString(expected) + ", actual " +
                    Arrays.toString(actual));
        }
    }

    public static void assertEquals(String testName, int[][] expected, int[][] actual) {
        if (Arrays.deepEquals(expected, actual)) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed: expected " + Arrays.deepToString(expected) + ", actual " +
                    Arrays.deepToString(actual));
        }
    }

    public static void assertEquals(String testName, List<String> expected, List<String> actual) {
        if (expected.size() == actual.size() && actual.containsAll(expected) && expected.containsAll(actual)) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed:" + "\n" + "expected ");
            for (String result : expected) {
                System.out.print(result);
                System.out.println();
            }
            System.out.println("actual ");
            for (String result : actual) {
                System.out.print(result);
                System.out.println();
            }
        }
    }

    public static void assertEquals(String testName, Object expected, Object actual) {
        if (expected.getClass().equals(actual.getClass())) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed: expected " + expected + ", actual " + actual);
        }
    }

    public static void assertEquals(String testName, Object[] expected, Object[] actual) {
        if (Arrays.equals(expected, actual)) {
            System.out.println(testName + " passed");
        } else {
            System.out.println(testName + " failed: expected " + arrayToString(expected) + ", actual " + arrayToString(actual));
        }
    }

    public static void fail(String msg) {
        throw new AssertionError();
    }

    private static String arrayToString(Object[] array) {
        String result = "{";
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                result += array[i].toString();
            } else {
                result += "null";
            }
            if (array.length - 1 != i ) {
                result += ", ";
            }
        }
        result += "}";
        return result;
    }
}
