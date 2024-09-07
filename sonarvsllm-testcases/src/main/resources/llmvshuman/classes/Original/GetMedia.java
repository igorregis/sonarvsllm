/**
 * Retorna a média aritmética dos valores registrados, ou zero se nenhum
 * valor foi registrado.
 *
 * <p> A média calculada pode variar numericamente e ter o
 * comportamento de caso especial como calcular a soma; veja {@link #getSoma}
 * para detalhes.
 *
 * @apiNote Valores ordenados por magnitude absoluta crescente tendem a produzir
 * resultados mais precisos.
 *
 * @return a média aritmética dos valores, ou zero se nenhum
 */
public final double getMedia() {
    return getContagem() > 0 ? getSoma() / getContagem() : 0.0d;
}