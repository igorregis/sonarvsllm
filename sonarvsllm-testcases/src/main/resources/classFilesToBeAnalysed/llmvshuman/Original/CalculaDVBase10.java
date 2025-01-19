/**
 * Calculo do Modulo 10 para geracao do digito verificador de boletos bancários.
 * <p>
 * Conforme o esquema abaixo, cada dígito do número, começando da direita para a esquerda
 * (menos significativo para o mais significativo) é multiplicado, na ordem, por 2, depois 1, depois 2, depois 1 e
 * assim sucessivamente.
 * Em vez de ser feito o somatório das multiplicações, será feito o somatório dos dígitos das multiplicações
 * (se uma multiplicação der 12, por exemplo, será somado 1 + 2 = 3).
 * O somatório será dividido por 10 e se o resto (módulo 10) for diferente de zero, o dígito será 10 menos este valor.
 * Número exemplo: 261533-4
 * +---+---+---+---+---+---+   +---+
 * | 2 | 6 | 1 | 5 | 3 | 3 | - | 4 |
 * +---+---+---+---+---+---+   +---+
 * |   |   |   |   |   |
 * x1  x2  x1  x2  x1  x2
 * |   |   |   |   |   |
 * =2 =12  =1 =10  =3  =6
 * +---+---+---+---+---+-> = (16 / 10) = 1, resto 6 => DV = (10 - 6) = 4
 */
public int calculaDVBase10(String num) {
    int soma = 0;
    String[] numeros = new String[num.length() + 1];

    for (int i = num.length(), n = 0; i > 0; i--, n++) {
        // Multiplica da direita pra esquerda, alternando os algarismos 2 e 1
        numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * (n % 2 == 0 ? 2 : 1));
    }
    // Realiza a soma dos campos de acordo com a regra
    for (int i = (numeros.length - 1); i > 0; i--) {
        if (numeros[i].length() > 1) {
            String digito1 = numeros[i].substring(0, numeros[i].length() - 1);
            String digito2 = numeros[i].substring(numeros[i].length() - 1, numeros[i].length());
            numeros[i] = String.valueOf(Integer.valueOf(digito1) + Integer.valueOf(digito2));
        }
    }
    // soma todos os elementos do array e calcula o digito verificador na base 10
    // de acordo com a regra.
    for (int i = numeros.length; i > 0; i--) {
        if (numeros[i - 1] != null) {
            soma += Integer.valueOf(numeros[i - 1]);
        }
    }
    // retorna o digito verificador
    return 10 - soma % 10;
}
