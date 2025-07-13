package com.alex.dcc025;

import com.alex.dcc025.constantes.Constantes;
import com.alex.dcc025.sistema.SistemaAcademico;

public class Trabalho2DCC025 {
    public static void main(String[] args) {
        SistemaAcademico sistema = new SistemaAcademico(Constantes.alunos.get(0), Constantes.todasDisciplinas);
        sistema.run();
    }
}