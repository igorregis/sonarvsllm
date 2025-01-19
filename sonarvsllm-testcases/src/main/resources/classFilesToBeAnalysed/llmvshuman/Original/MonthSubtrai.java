/**
 * Retorna o mês do ano que é o número especificado de meses antes deste.
 * <p>
 * O cálculo rola ao redor do início do ano de janeiro a dezembro.
 * O período especificado pode ser negativo.
 * <p>
 * Esta instância é imutável e não é afetada por esta chamada de método.
 *
 * @param meses os meses a subtrair, positivos ou negativos
 * @return o mês resultante, não nulo
 */
public Month subtrai(long meses) {
    return soma(-(meses % 12));
}