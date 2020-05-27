package com.dev.republica.model;

public enum Authorities {

    ROLE_SEMTETO, ROLE_MORADOR, ROLE_REPRESENTANTE;

    public static String[] names() {
        String[] names = new String[values().length];
        for (int index = 0; index < values().length; index++) {
            names[index] = values()[index].name();
        }

        return names;
    }

}
