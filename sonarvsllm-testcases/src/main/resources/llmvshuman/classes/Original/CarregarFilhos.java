/**
 * Chamado a primeira vez que getContagemFilhos é chamado. Cria
 * filhos com nomes aleatórios de nomes.
 */
protected void carregarFilhos() {
    DynamicTreeNode novoNo;
    Font fonte;
    int indiceAleatorio;
    DadosAmostra dados;

    for (int contador = 0; contador < DynamicTreeNode.CONTAGEM_FILHOS_PADRAO;
         contador++) {
        indiceAleatorio = (int) (geradorNomes.nextFloat() * contagemNomes);
        String stringExibicao = NOMES[indiceAleatorio];
        if (fontes == null || fontes[indiceAleatorio].canDisplayUpTo(stringExibicao) != -1) {
            fonte = null;
        } else {
            fonte = fontes[indiceAleatorio];
        }

        if (contador % 2 == 0) {
            dados = new DadosAmostra(fonte, Color.red, stringExibicao);
        } else {
            dados = new DadosAmostra(fonte, Color.blue, stringExibicao);
        }
        novoNo = new DynamicTreeNode(dados);
            /* Não use add() aqui, add chama insert(novoNo, getContagemFilhos())
            então se você quiser usar add, apenas certifique-se de definir carregado = true
            primeiro. */
        insert(novoNo, contador);
    }
    /* Este nó agora foi carregado, marque-o como tal. */
    carregado = true;
}