package MyArrays.practice;

import java.util.Optional;
import java.util.function.UnaryOperator;

public interface Search<E extends Number & Comparable<E>, R extends Number>  {

    Optional<R> searchInRange(E key, Range<E> range, Optional<Func<E>> func);

    @FunctionalInterface
    interface Func<E extends Number> extends UnaryOperator<E> {};

    final class Range<E extends Number & Comparable<E>> {
        private final E min;
        private final E max;

        public Range(E min, E max) {
            this.min = min;
            this.max = max;
        }

        public E getMin() {
            return min;
        }

        public E getMax() {
            return max;
        }

        public boolean validateRange() {
            E zero = getZeroValue();
            return max.compareTo(min) > 0 && max.compareTo(zero) > 0 && min.compareTo(zero) >= 0;
        }

        @SuppressWarnings("unchecked")
        private E getZeroValue() {
            if (min instanceof Integer) {
                return (E) Integer.valueOf(0);
            } else if (min instanceof Double) {
                return (E) Double.valueOf(0.0);
            } else if (min instanceof Long) {
                return (E) Long.valueOf(0L);
            } else if (min instanceof Float) {
                return (E) Float.valueOf(0.0f);
            } else if (min instanceof Short) {
                return (E) Short.valueOf((short) 0);
            } else if (min instanceof Byte) {
                return (E) Byte.valueOf((byte) 0);
            } else {
                throw new UnsupportedOperationException("Unsupported Number type");
            }

        }
    }
}
