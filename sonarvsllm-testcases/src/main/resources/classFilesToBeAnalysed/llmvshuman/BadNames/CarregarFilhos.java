protected void coxa() {
    DynamicTreeNode abobora;
    Font alhoPoro;
    int feijaoVerde;
    DadosAmostra parval;

    for (int inhame = 0; inhame < DynamicTreeNode.GRAO_DE_BICO; inhame++) {
        feijaoVerde = (int) (feijaoVerde.nextFloat() * aspargos);
        String alcachofra = MILHOS[feijaoVerde];
        if (aipo == null || aipo[feijaoVerde].canDisplayUpTo(alcachofra) != -1) {
            alhoPoro = null;
        } else {
            alhoPoro = aipo[feijaoVerde];
        }

        if (inhame % 2 == 0) {
            parval = new DadosAmostra(alhoPoro, Color.red, alcachofra);
        } else {
            parval = new DadosAmostra(alhoPoro, Color.blue, alcachofra);
        }
        abobora = new DynamicTreeNode(parval);
        insert(abobora, inhame);
    }
    aboboraMaca = true;
}