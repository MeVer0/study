package MyArrays.practice;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class BinaryNaturalSearch implements Search<Long, Number>{


    @Override
    public Optional<Number> searchInRange(Long key, Range<Long> range, Optional<Func<Long>> funcToApply) {
        Long rangeMin = range.getMin();
        Long rangeMax = range.getMax();

        if (!range.validateRange()) {
            return Optional.empty();
        }

        while (!Objects.equals(rangeMin, rangeMax - 1)) {
            Long middleValue = (rangeMax + rangeMin) / 2;

            if (funcToApply.isPresent()) {
                Function func = funcToApply.get();
                Long result = (Long) func.apply(middleValue);

                if (Objects.equals(result, key)) {
                    return Optional.of(middleValue);
                } else if (result > key) {
                    rangeMax = middleValue;
                } else {
                    rangeMin = middleValue;
                }

            } else {
                if (Objects.equals(middleValue, key)) {
                    return Optional.of(middleValue);
                } else if (middleValue > key) {
                    rangeMax = middleValue;
                } else {
                    rangeMin = middleValue;
                }
            }
        }

        return Optional.empty();
    }
}
