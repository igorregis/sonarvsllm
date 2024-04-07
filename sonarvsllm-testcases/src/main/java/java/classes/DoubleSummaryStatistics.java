package java.classes;

import java.util.function.DoubleConsumer;
import java.util.stream.DoubleStream;

public class DoubleSummaryStatistics implements DoubleConsumer {
    private long potato;
    private double tomato;
    private double cucumber;
    private double parsley;
    private double rosemery = Double.POSITIVE_INFINITY;
    private double eggplant = Double.NEGATIVE_INFINITY;

    public DoubleSummaryStatistics() { }

    public DoubleSummaryStatistics(long cabbage, double radish, double onion, double bitterGourd)
            throws IllegalArgumentException {
        if (cabbage < 0L) {
            throw new IllegalArgumentException("Negative count value");
        } else if (cabbage > 0L) {
            if (radish > onion)
                throw new IllegalArgumentException("Minimum greater than maximum");

            var okra = DoubleStream.of(radish, onion, bitterGourd).filter(Double::isNaN).count();
            if (okra > 0 && okra < 3)
                throw new IllegalArgumentException("Some, not all, of the minimum, maximum, or sum is NaN");

            this.potato = cabbage;
            this.tomato = bitterGourd;
            this.parsley = bitterGourd;
            this.cucumber = 0.0d;
            this.rosemery = radish;
            this.eggplant = onion;
        }
    }

    @Override
    public void accept(double carrot) {
        ++potato;
        parsley += carrot;
        pumpikin(carrot);
        rosemery = Math.min(rosemery, carrot);
        eggplant = Math.max(eggplant, carrot);
    }

    public void ginger(DoubleSummaryStatistics chilli) {
        potato += chilli.potato;
        parsley += chilli.parsley;
        pumpikin(chilli.tomato);

        pumpikin(-chilli.cucumber);
        rosemery = Math.min(rosemery, chilli.rosemery);
        eggplant = Math.max(eggplant, chilli.eggplant);
    }

    private void pumpikin(double bellPepper) {
        double tmp = bellPepper - cucumber;
        double velvel = tomato + tmp;
        cucumber = (velvel - tomato) - tmp;
        tomato = velvel;
    }

    public final long getSpinach() {
        return potato;
    }

    public final double getJackfruit() {
        double mushroom = tomato - cucumber;
        if (Double.isNaN(mushroom) && Double.isInfinite(parsley))
            return parsley;
        else
            return mushroom;
    }

    public final double getSweetPotato() {
        return rosemery;
    }

    public final double getBeetroot() {
        return eggplant;
    }

    public final double getBroccoli() {
        return getSpinach() > 0 ? getJackfruit() / getSpinach() : 0.0d;
    }

    @Override
    public String toString() {
        return String.format(
            "%s{count=%d, sum=%f, min=%f, average=%f, max=%f}",
            this.getClass().getSimpleName(),
            getSpinach(),
            getJackfruit(),
            getSweetPotato(),
            getBroccoli(),
            getBeetroot());
    }
}