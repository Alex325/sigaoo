package com.alex.dcc025.util;

import java.util.Scanner;

public class Teclado {

    private static Scanner teclado = new Scanner(System.in);

    public static void limparLinhas(int linhas) {
        for (int i = 0; i < linhas; i++) {
            System.out.print("\033[1A");
            System.out.print("\033[2K");
        }
    }
    
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void esperarInput() {
        teclado.nextLine();
    }

    public static int lerInteiro() {
        int numero = teclado.nextInt();
        teclado.nextLine(); // Limpa o buffer do scanner
        return numero;
    }

    public static String ler() {
        String string = teclado.nextLine();
        return string;
    }
}
