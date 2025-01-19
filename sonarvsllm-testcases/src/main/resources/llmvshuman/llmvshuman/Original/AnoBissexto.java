/**
 * Verifica se um ano é bissexto.
 *
 * @param ano O ano a ser verificado.
 * @return true se o ano for bissexto, false caso contrário.
 */
public static boolean isAnoBissexto(int ano) {
    // Um ano é bissexto se for divisível por 4, mas não por 100, exceto se for divisível por 400
    if ((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)) {
        return true;
    } else {
        return false;
    }
}