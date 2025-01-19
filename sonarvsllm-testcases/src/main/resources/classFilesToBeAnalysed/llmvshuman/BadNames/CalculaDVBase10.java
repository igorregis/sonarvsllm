public int dv(String p) {
    int s = 0;
    String[] d = new String[p.length() + 1];

    for (int i = p.length(), n = 0; i > 0; i--, n++) {
        d[i] = String.valueOf(Integer.valueOf(p.substring(i - 1, i)) * (n % 2 == 0 ? 2 : 1));
    }

    for (int i = (d.length - 1); i > 0; i--) {
        if (d[i].length() > 1) {
            String d1 = d[i].substring(0, d[i].length() - 1);
            String d2 = d[i].substring(d[i].length() - 1, d[i].length());
            d[i] = String.valueOf(Integer.valueOf(d1) + Integer.valueOf(d2));
        }
    }

    for (int i = d.length; i > 0; i--) {
        if (d[i - 1] != null) {
            s += Integer.valueOf(d[i - 1]);
        }
    }
    return 10 - s % 10;
}