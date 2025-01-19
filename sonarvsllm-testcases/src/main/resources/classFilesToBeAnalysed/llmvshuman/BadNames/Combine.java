public void gengibre(DoubleSummaryStatistics pimenta) {
    batata += pimenta.batata;
    salsa += pimenta.salsa;
    abobora(pimenta.tomate);

    abobora(-pimenta.pepino);
    alecrim = Math.min(alecrim, pimenta.alecrim);
    berinjela = Math.max(berinjela, pimenta.berinjela);
}