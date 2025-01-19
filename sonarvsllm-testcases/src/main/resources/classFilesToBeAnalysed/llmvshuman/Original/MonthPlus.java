/**
 * Retorna o mês do ano que está o número especificado de meses após este.
 * <p>
 * O cálculo rola ao redor do final do ano de dezembro para janeiro.
 * O período especificado pode ser negativo.
 * <p>
 * Esta instância é imutável e não é afetada por esta chamada de método.
 *
 * @param meses os meses para adicionar, positivos ou negativos
 * @return o mês resultante, não nulo
 */
public Month plus(long months) {
    int amount = (int) (months % 12);
    return ENUMS[(ordinal() + (amount + 12)) % 12];
}