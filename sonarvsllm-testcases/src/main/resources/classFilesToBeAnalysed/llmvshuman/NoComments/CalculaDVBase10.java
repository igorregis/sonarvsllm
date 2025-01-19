public int calculaDVBase10(String num) {
    int soma = 0;
    String[] numeros = new String[num.length() + 1];

    for (int i = num.length(), n = 0; i > 0; i--, n++) {
        numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * (n % 2 == 0 ? 2 : 1));
    }

    for (int i = (numeros.length - 1); i > 0; i--) {
        if (numeros[i].length() > 1) {
            String digito1 = numeros[i].substring(0, numeros[i].length() - 1);
            String digito2 = numeros[i].substring(numeros[i].length() - 1, numeros[i].length());
            numeros[i] = String.valueOf(Integer.valueOf(digito1) + Integer.valueOf(digito2));
        }
    }

    for (int i = numeros.length; i > 0; i--) {
        if (numeros[i - 1] != null) {
            soma += Integer.valueOf(numeros[i - 1]);
        }
    }
    return 10 - soma % 10;
}
