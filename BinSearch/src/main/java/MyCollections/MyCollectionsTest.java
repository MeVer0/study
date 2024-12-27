import java.util.*;

public class MyCollectionsTest {

    public static void main(String[] args) {
        runTests();
    }

    public static void runTests() {
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
        assert Collections.binarySearch(intList, 3) == 2;
        assert Collections.binarySearch(intList, 6) < 0;

        List<String> stringList = Arrays.asList("apple", "banana", "cherry", "date");
        assert Collections.binarySearch(stringList, "cherry") == 2;
        assert Collections.binarySearch(stringList, "fig") < 0;

        List<Integer> descendingIntList = Arrays.asList(5, 4, 3, 2, 1);
        Comparator<Integer> descendingComparator = Comparator.reverseOrder();
        assert Collections.binarySearch(descendingIntList, 3, descendingComparator) == 2;
        assert Collections.binarySearch(descendingIntList, 6, descendingComparator) < 0;

        List<String> caseInsensitiveList = Arrays.asList("Apple", "banana", "Cherry", "Date");
        Comparator<String> caseInsensitiveComparator = String.CASE_INSENSITIVE_ORDER;
        assert Collections.binarySearch(caseInsensitiveList, "cherry", caseInsensitiveComparator) == 2;
        assert Collections.binarySearch(caseInsensitiveList, "fig", caseInsensitiveComparator) < 0;

        List<Integer> emptyList = Collections.emptyList();
        assert Collections.binarySearch(emptyList, 1) < 0;

        System.out.println("All tests passed successfully.");
    }
}
