/**
 * Combina o estado de outro {@code DoubleSummaryStatistics} neste.
 *
 * @param other outro {@code DoubleSummaryStatistics}
 * @throws NullPointerException se {@code other} for nulo
 */
public void combine(DoubleSummaryStatistics other) {
    count += other.count;
    simpleSum += other.simpleSum;
    sumWithCompensation(other.sum);

    // Subtrai bits de compensação
    sumWithCompensation(-other.sumCompensation);
    min = Math.min(min, other.min);
    max = Math.max(max, other.max);
}