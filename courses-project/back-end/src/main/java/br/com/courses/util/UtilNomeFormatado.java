package br.com.courses.util;

public class UtilNomeFormatado {

    public static boolean nomeValido(String nome) {

        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        String[] partes = nome.split(" ");

        if (!validarPrimeiraLetraMaiuscula(partes[0])) {
            return false;
        }

        for (int i = 1; i < partes.length; i++) {
            String parte = partes[i];


            if (isPreposicao(parte)) {
                if (!parte.equals(parte.toLowerCase())) {
                    return false;
                }
            } else {

                if (!validarPrimeiraLetraMaiuscula(parte)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean validarPrimeiraLetraMaiuscula(String palavra) {
        return palavra != null && palavra.length() > 1 && Character.isUpperCase(palavra.charAt(0));
    }


    private static boolean isPreposicao(String palavra) {

        String[] preposicoes = {"de", "da", "do", "das", "dos"};

        for (String preposicao : preposicoes) {

            if (palavra.equalsIgnoreCase(preposicao)) {
                return true;
            }

        }

        return false;

    }

}
