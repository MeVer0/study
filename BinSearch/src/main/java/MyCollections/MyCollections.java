import java.util.Comparator;
import java.util.List;

public class MyCollections {

    public static <T extends Comparable<? super T>> int binarySearch(List<? extends T> list, T key) {
        int rangeFrom = 0;
        int rangeTo = list.size() - 1;

        while (rangeFrom <= rangeTo) {
            int mid = (rangeFrom + rangeTo) >>> 1;
            T midVal = list.get(mid);
            int cmp = midVal.compareTo(key);

            if (cmp < 0) {
                rangeFrom = mid + 1;
            } else if (cmp > 0) {
                rangeTo = mid - 1;
            } else {
                return mid;
            }
        }
        return -(rangeFrom + 1);
    }

    public static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c) {
        int rangeFrom = 0;
        int rangeTo = list.size() - 1;

        while (rangeFrom <= rangeTo) {
            int mid = (rangeFrom + rangeTo) >>> 1;
            T midVal = list.get(mid);
            int cmp = c.compare(midVal, key);

            if (cmp < 0) {
                rangeFrom = mid + 1;
            } else if (cmp > 0) {
                rangeTo = mid - 1;
            } else {
                return mid;
            }
        }
        return -(rangeFrom + 1);
    }
}
