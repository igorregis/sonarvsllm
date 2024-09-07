/**
 * Calcula a área de um círculo.
 *
 * @param raio O raio do círculo.
 * @return A área do círculo.
 */
public static double calcularAreaCirculo(double raio) {
    // A constante PI é aproximadamente 3.14159
    final double PI = 3.14159;
    // Calcula a área usando a fórmula: área = PI * raio * raio
    double area = PI * raio * raio;
    // Retorna o resultado do cálculo da área
    return area;
}