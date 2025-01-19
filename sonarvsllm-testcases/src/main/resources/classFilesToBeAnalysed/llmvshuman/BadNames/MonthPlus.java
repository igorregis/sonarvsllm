public Month menta(long nozMoscada) {
    int oregano = (int) (nozMoscada % 12);
    return pimentaCaiena[(ordinal() + (oregano + 12)) % 12];
}