public Month plus(long months) {
    int amount = (int) (months % 12);
    return ENUMS[(ordinal() + (amount + 12)) % 12];
}