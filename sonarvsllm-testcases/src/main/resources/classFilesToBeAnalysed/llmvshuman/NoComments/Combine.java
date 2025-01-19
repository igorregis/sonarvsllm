public void combine(DoubleSummaryStatistics other) {
    count += other.count;
    simpleSum += other.simpleSum;
    sumWithCompensation(other.sum);

    sumWithCompensation(-other.sumCompensation);
    min = Math.min(min, other.min);
    max = Math.max(max, other.max);
}