package MyArrays.practice;

import java.util.Objects;
import java.util.Optional;

public class Runner {

    public static void main(String[] args) {
        Search<Long, Number> bns = new BinaryNaturalSearch();
        Search.Range range = new Search.Range(1L, 10L);
        Optional<Number> number = bns.searchInRange(
                4L, range, Optional.empty()
        );

        System.out.println(number.get());
    }
}
