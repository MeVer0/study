package MyArrays;

import java.lang.reflect.Array;
import java.util.Comparator;

public class MyArrays {

    static private boolean checkRange(Object object, int fromIndex, int toIndex) {
        if (object != null && object.getClass().isArray()) {
            int length = Array.getLength(object);
            return fromIndex >= 0 && toIndex >= 0 && fromIndex <= length && toIndex <= length;
        }
        return false;
    }


    static int binarySearch(byte[] a, int fromIndex, int toIndex, byte key) {
        if (!checkRange(a, fromIndex, toIndex)) {
            System.out.println("WRONG DATA");
            return -1;
        }

        int rangeFrom = fromIndex;
        int rangeTo = toIndex - 1;

        while (rangeFrom < rangeTo) {
            int mid = (rangeFrom + rangeTo) >>> 1;
            byte midValue = a[mid];

            if (key > midValue) {
                rangeFrom = mid + 1;
            } else if (key < midValue) {
                rangeTo = mid - 1;
            } else {
                return mid;
            }
        }
        return -(rangeFrom + 1);
    };

    static int binarySearch(byte[] a, byte key) {
        return binarySearch(a, 0, a.length, key);
    }

    static int binarySearch(char[] a, int fromIndex, int toIndex, char key) {
        if (!checkRange(a, fromIndex, toIndex)) {
            System.out.println("WRONG DATA");
            return -1;
        }

        int rangeFrom = fromIndex;
        int rangeTo = toIndex - 1;

        while (rangeFrom < rangeTo) {
            int mid = (rangeFrom + rangeTo) >>> 1;
            char midValue = a[mid];

            if (key > midValue) {
                rangeFrom = mid + 1;
            } else if (key < midValue) {
                rangeTo = mid - 1;
            } else {
                return mid;
            }
        }
        return -(rangeFrom + 1);
    };

    static int binarySearch(char[] a, char key){
        return binarySearch(a, 0, a.length, key);
    }

    static int binarySearch(double[] a, int fromIndex, int toIndex, double key) {
        if (!checkRange(a, fromIndex, toIndex)) {
            System.out.println("WRONG DATA");
            return -1;
        }

        int rangeFrom = fromIndex;
        int rangeTo = toIndex - 1;

        while (rangeFrom <= rangeTo) {
            int mid = (rangeFrom + rangeTo) >>> 1;
            double midVal = a[mid];

            if (midVal < key)
                rangeFrom = mid + 1;  // Neither val is NaN, thisVal is smaller
            else if (midVal > key)
                rangeTo = mid - 1; // Neither val is NaN, thisVal is larger
            else {
                long midBits = Double.doubleToLongBits(midVal);
                long keyBits = Double.doubleToLongBits(key);
                if (midBits == keyBits)     // Values are equal
                    return mid;             // Key found
                else if (midBits < keyBits) // (-0.0, 0.0) or (!NaN, NaN)
                    rangeFrom = mid + 1;
                else                        // (0.0, -0.0) or (NaN, !NaN)
                    rangeTo = mid - 1;
            }
        }
        return -(rangeFrom + 1);  // key not found.
    }

    static int binarySearch(double[] a, double key){
        return binarySearch(a, 0, a.length, key);
    }

    static int binarySearch(float[] a, int fromIndex, int toIndex, float key){

        int rangeFrom = fromIndex;
        int rangeTo = toIndex - 1;

        while (rangeFrom <= rangeTo) {
            int mid = (rangeFrom + rangeTo) >>> 1;
            float midVal = a[mid];

            if (midVal < key)
                rangeFrom = mid + 1;
            else if (midVal > key)
                rangeTo = mid - 1;
            else {
                int midBits = Float.floatToIntBits(midVal);
                int keyBits = Float.floatToIntBits(key);
                if (midBits == keyBits)
                    return mid;
                else if (midBits < keyBits)
                    rangeFrom = mid + 1;
                else
                    rangeTo = mid - 1;
            }
        }
        return -(rangeFrom + 1);
    }

    static int binarySearch(float[] a, float key) {
        return binarySearch(a, 0, a.length, key);
    }

    static int binarySearch(int[] a, int fromIndex, int toIndex, int key){
        if (!checkRange(a, fromIndex, toIndex)) {
            System.out.println("WRONG DATA");
            return -1;
        }
        int rangeFrom = fromIndex;
        int rangeTo = toIndex - 1;

        while (rangeFrom < rangeTo) {
            int mid = (rangeFrom + rangeTo) >>> 1;
            int midValue = a[mid];

            if (key > midValue) {
                rangeFrom = mid + 1;
            } else if (key < midValue) {
                rangeTo = mid - 1;
            } else {
                return mid;
            }
        }
        return -(rangeFrom + 1);
    }

    static int binarySearch(int[] a, int key){
        return binarySearch(a, 0, a.length, key);
    }

    static int binarySearch(long[] a, int fromIndex, int toIndex, long key) {
        if (!checkRange(a, fromIndex, toIndex)) {
            System.out.println("WRONG DATA");
            return -1;
        }
        int rangeFrom = fromIndex;
        int rangeTo = toIndex - 1;

        while (rangeFrom <= rangeTo) {
            int mid = (rangeFrom + rangeTo) >>> 1;
            long midVal = a[mid];

            if (midVal < key)
                rangeFrom = mid + 1;
            else if (midVal > key)
                rangeTo = mid - 1;
            else
                return mid;
        }
        return -(rangeFrom + 1);
    }

    static int binarySearch(long[] a, long key){
        return binarySearch(a, 0, a.length, key);
    }

    static int binarySearch(short[] a, int fromIndex, int toIndex, short key) {
        if (!checkRange(a, fromIndex, toIndex)) {
            System.out.println("WRONG DATA");
            return -1;
        }

        int rangeFrom = fromIndex;
        int rangeTo = toIndex - 1;

        while (rangeFrom <= rangeTo) {
            int mid = (rangeFrom + rangeTo) >>> 1;
            short midVal = a[mid];

            if (midVal < key)
                rangeFrom = mid + 1;
            else if (midVal > key)
                rangeTo = mid - 1;
            else
                return mid; // key found
        }
        return -(rangeFrom + 1);  // key not found.
    }

    static int binarySearch(short[] a, short key) {
        return binarySearch(a, 0, a.length, key);
    }

    static <T> int binarySearch(T[] a, int fromIndex, int toIndex, T key, Comparator c){
        int rangeFrom = fromIndex;
        int rangeTo = toIndex - 1;

        while (rangeFrom <= rangeTo) {
            int mid = (rangeFrom + rangeTo) >>> 1;
            T midVal = a[mid];
            int cmp = c.compare(midVal, key);
            if (cmp < 0)
                rangeFrom = mid + 1;
            else if (cmp > 0)
                rangeTo = mid - 1;
            else
                return mid; // key found
        }
        return -(rangeFrom + 1);
    }

    static <T> int binarySearch(T[] a, T key, Comparator c){
        return binarySearch(a, 0, a.length, key, c);
    }

}

