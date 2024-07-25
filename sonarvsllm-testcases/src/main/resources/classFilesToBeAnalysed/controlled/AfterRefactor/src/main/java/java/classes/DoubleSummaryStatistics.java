package java.classes;

import java.util.function.DoubleConsumer;
import java.util.stream.DoubleStream;

public class DoubleSummaryStatistics implements DoubleConsumer {
    private long count;
    private double sum;
    private double sumCompensation;
    private double simpleSum;
    private double min = Double.POSITIVE_INFINITY;
    private double max = Double.NEGATIVE_INFINITY;

    public DoubleSummaryStatistics() { }

    /**
     * Constructs a DoubleSummaryStatistics instance.
     *
     * @param count the count of numbers
     * @param min the minimum value
     * @param max the maximum value
     * @param sum the sum of all numbers
     * @throws IllegalArgumentException if count is negative, min is greater than max, or some but not all of min, max, and sum are NaN
     */
    public DoubleSummaryStatistics(long count, double min, double max, double sum) throws IllegalArgumentException {
        // Check if count is negative
        if (count < 0L) {
            throw new IllegalArgumentException("Negative count value");
        }

        if (count > 0L) {
            // Check if min is greater than max
            if (min > max) {
                throw new IllegalArgumentException("Minimum greater than maximum");
            }

            // Count the number of NaN values among min, max, and sum
            long nanCount = DoubleStream.of(min, max, sum).filter(Double::isNaN).count();

            // Check if some, but not all, of the values are NaN
            if (nanCount > 0 && nanCount < 3) {
                throw new IllegalArgumentException("Some, not all, of the minimum, maximum, or sum is NaN");
            }

            // Assign values to instance variables
            this.count = count;
            this.sum = sum;
            this.simpleSum = sum;
            this.sumCompensation = 0.0d;
            this.min = min;
            this.max = max;
        }
    }


    @Override
    public void accept(double value) {
        ++count;
        simpleSum += value;
        sumWithCompensation(value);
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    /**
     * Combines the statistics of the current object with another DoubleSummaryStatistics object.
     *
     * @param other the other DoubleSummaryStatistics object
     */
    public void combine(DoubleSummaryStatistics other) {
        // Add the count of the other object to the current count
        count += other.count;

        // Add the simple sum of the other object to the current simple sum
        simpleSum += other.simpleSum;

        // Add the sum of the other object to the current sum with compensation
        sumWithCompensation(other.sum);

        // Subtract the sum compensation of the other object from the current sum with compensation
        sumWithCompensation(-other.sumCompensation);

        // Update the minimum value
        min = Math.min(min, other.min);

        // Update the maximum value
        max = Math.max(max, other.max);
    }


    private void sumWithCompensation(double value) {
        double tmp = value - sumCompensation;
        double velvel = sum + tmp;
        sumCompensation = (velvel - sum) - tmp;
        sum = velvel;
    }

    public final long getCount() {
        return count;
    }

    public final double getSum() {
        double tmp =  sum - sumCompensation;
        if (Double.isNaN(tmp) && Double.isInfinite(simpleSum))
            return simpleSum;
        else
            return tmp;
    }

    public final double getMin() {
        return min;
    }

    public final double getMax() {
        return max;
    }

    public final double getAverage() {
        return getCount() > 0 ? getSum() / getCount() : 0.0d;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{count=%d, sum=%f, min=%f, average=%f, max=%f}",
                this.getClass().getSimpleName(),
                getCount(),
                getSum(),
                getMin(),
                getAverage(),
                getMax());
    }
}