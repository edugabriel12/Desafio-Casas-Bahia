package br.com.desafiocasasbahia.helpers;

public class VendedorValidator {

    public static boolean validaCPF(String documento) {
        documento = documento.replaceAll("[^0-9]", "");

        if (documento.length() != 11) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (documento.charAt(i) - '0') * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 > 9) {
            digito1 = 0;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (documento.charAt(i) - '0') * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 > 9) {
            digito2 = 0;
        }

        return (digito1 == documento.charAt(9) - '0') && (digito2 == documento.charAt(10) - '0');

    }

    public static boolean validaCNPJ(String documento) {
        documento = documento.replaceAll("[^0-9]", "");

        if (documento.length() != 14) {
            return false;
        }

        int soma = 0;
        int peso = 5;
        for (int i = 0; i < 12; i++) {
            soma += (documento.charAt(i) - '0') * peso;
            peso--;
            if (peso == 1) {
                peso = 9;
            }
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 > 9) {
            digito1 = 0;
        }

        soma = 0;
        peso = 6;
        for (int i = 0; i < 13; i++) {
            soma += (documento.charAt(i) - '0') * peso;
            peso--;
            if (peso == 1) {
                peso = 9;
            }
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 > 9) {
            digito2 = 0;
        }

        return (digito1 == documento.charAt(12) - '0') && (digito2 == documento.charAt(13) - '0');
    }

    public static boolean validaData(String data) {
        String regex = "^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        return data.matches(regex);
    }
}
