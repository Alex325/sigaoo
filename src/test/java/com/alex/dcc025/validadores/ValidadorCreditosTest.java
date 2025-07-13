package com.alex.dcc025.validadores;

import org.junit.jupiter.api.Test;

import com.alex.dcc025.aluno.Aluno;
import com.alex.dcc025.disciplinas.DisciplinaObrigatoria;

import static org.junit.jupiter.api.Assertions.*;

public class ValidadorCreditosTest {
    
    @Test
    public void testCreditosValidosDisciplinasValidas() {

        try {
            DisciplinaObrigatoria disciplina = new DisciplinaObrigatoria("DCC025", "Orientação a Objetos", null, null, 4);

            final int creditosMinimos = 4;
            ValidadorCreditos validador = new ValidadorCreditos(creditosMinimos);

            Aluno aluno = new Aluno("Alex", "202400001");
            aluno.getHistorico().put(disciplina, 60);

            assertTrue(validador.validar(aluno));
        } catch (Exception e) {
            fail("Não deveria ter exceção: " + e.getMessage());
        }
    }

    @Test
    public void testCreditosInvalidosDisciplinasValidas() {
        try {
            
            DisciplinaObrigatoria disciplina = new DisciplinaObrigatoria("DCC025", "Orientação a Objetos", null, null, 4);

            final int creditosMinimos = 12;
            ValidadorCreditos validador = new ValidadorCreditos(creditosMinimos);

            Aluno aluno = new Aluno("Alex", "202400001");
            aluno.getHistorico().put(disciplina, 60);

            assertFalse(validador.validar(aluno));
        } catch (Exception e) {
            fail("Não deveria ter exceção: " + e.getMessage());
        }
    }

    @Test
    public void testCreditosValidosAlgumasDisciplinasInvalidas() {
        try {
            DisciplinaObrigatoria disciplina1 = new DisciplinaObrigatoria("DCC025", "Orientação a Objetos", null, null, 4);
            DisciplinaObrigatoria disciplina2 = new DisciplinaObrigatoria("DCC026", "Estruturas de Dados", null, null, 4);
            DisciplinaObrigatoria disciplina3 = new DisciplinaObrigatoria("DCC027", "Algoritmos II", null, null, 6);

            final int creditosMinimos = 8;
            ValidadorCreditos validador = new ValidadorCreditos(creditosMinimos);

            Aluno aluno = new Aluno("Alex", "202400001");

            aluno.getHistorico().put(disciplina1, 60);
            aluno.getHistorico().put(disciplina2, 60);
            aluno.getHistorico().put(disciplina3, 59);

            assertTrue(validador.validar(aluno));
        } catch (Exception e) {
            fail("Não deveria ter exceção: " + e.getMessage());
        }
    }

    @Test
    public void testCreditosInvalidosDisciplinasInvalidas() {

        try {
            DisciplinaObrigatoria disciplina1 = new DisciplinaObrigatoria("DCC025", "Orientação a Objetos", null, null, 4);
            DisciplinaObrigatoria disciplina2 = new DisciplinaObrigatoria("DCC026", "Estruturas de Dados", null, null, 4);
            DisciplinaObrigatoria disciplina3 = new DisciplinaObrigatoria("DCC027", "Algoritmos II", null, null, 6);

            final int creditosMinimos = 12;
            ValidadorCreditos validador = new ValidadorCreditos(creditosMinimos);

            Aluno aluno = new Aluno("Alex", "202400001");
            aluno.getHistorico().put(disciplina1, 59);
            aluno.getHistorico().put(disciplina2, 60);
            aluno.getHistorico().put(disciplina3, 60);

            assertFalse(validador.validar(aluno));
        } catch (Exception e) {
            fail("Não deveria ter exceção: " + e.getMessage());
        }
    }

}
