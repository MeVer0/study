package MyArrays;

import java.util.Comparator;

public class MyArraysTest {

    public static void main(String[] args) {
        runTests();
    }

    public static void runTests() {
        // Test case 1: byte array
        byte[] byteArray = {1, 2, 3, 4, 5};
        assert MyArrays.binarySearch(byteArray, 0, byteArray.length, (byte) 3) == 2;
        assert MyArrays.binarySearch(byteArray, 0, byteArray.length, (byte) 6) < 0;

        // Test case 2: char array
        char[] charArray = {'a', 'b', 'c', 'd', 'e'};
        assert MyArrays.binarySearch(charArray, 0, charArray.length, 'c') == 2;
        assert MyArrays.binarySearch(charArray, 0, charArray.length, 'z') < 0;

        // Test case 3: double array
        double[] doubleArray = {1.1, 2.2, 3.3, 4.4, 5.5};
        assert MyArrays.binarySearch(doubleArray, 0, doubleArray.length, 4.4) == 3;
        assert MyArrays.binarySearch(doubleArray, 0, doubleArray.length, 6.6) < 0;

        // Test case 4: int array
        int[] intArray = {10, 20, 30, 40, 50};
        assert MyArrays.binarySearch(intArray, 0, intArray.length, 30) == 2;
        assert MyArrays.binarySearch(intArray, 0, intArray.length, 60) < 0;

        // Test case 5: float array
        float[] floatArray = {1.1f, 2.2f, 3.3f, 4.4f, 5.5f};
        assert MyArrays.binarySearch(floatArray, 0, floatArray.length, 4.4f) == 3;
        assert MyArrays.binarySearch(floatArray, 0, floatArray.length, 6.6f) < 0;

        // Test case 6: short array
        short[] shortArray = {100, 200, 300, 400, 500};
        assert MyArrays.binarySearch(shortArray, 0, shortArray.length, (short) 300) == 2;
        assert MyArrays.binarySearch(shortArray, 0, shortArray.length, (short) 600) < 0;

        // Test case 7: long array
        long[] longArray = {1000L, 2000L, 3000L, 4000L, 5000L};
        assert MyArrays.binarySearch(longArray, 0, longArray.length, 3000L) == 2;
        assert MyArrays.binarySearch(longArray, 0, longArray.length, 6000L) < 0;

        // Test case 8: generic array with comparator
        String[] stringArray = {"apple", "banana", "cherry", "date"};
        assert MyArrays.binarySearch(stringArray, 0, stringArray.length, "cherry", Comparator.naturalOrder()) == 2;
        assert MyArrays.binarySearch(stringArray, 0, stringArray.length, "fig", Comparator.naturalOrder()) < 0;

        System.out.println("All tests passed successfully.");
    }
}
