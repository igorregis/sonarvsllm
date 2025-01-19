/**
 * Obtém o mês correspondente ao primeiro mês deste trimestre.
 * <p>
 * O ano pode ser dividido em quatro trimestres.
 * Este método retorna o primeiro mês do trimestre para o mês base.
 * Janeiro, Fevereiro e Março retornam Janeiro.
 * Abril, Maio e Junho retornam Abril.
 * Julho, Agosto e Setembro retornam Julho.
 * Outubro, Novembro e Dezembro retornam Outubro.
 *
 * @return o primeiro mês do trimestre correspondente a este mês, não nulo
 */
public Month primeiroMesDoTrimestre() {
    return ENUMS[(ordinal() / 3) * 3];
}