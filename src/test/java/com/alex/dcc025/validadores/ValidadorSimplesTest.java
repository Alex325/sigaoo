package com.alex.dcc025.validadores;

import org.junit.jupiter.api.Test;

import com.alex.dcc025.aluno.Aluno;
import com.alex.dcc025.disciplinas.DisciplinaObrigatoria;


import static org.junit.jupiter.api.Assertions.*;

public class ValidadorSimplesTest {

    @Test
    public void testDisciplinaValida() {

        try {
            DisciplinaObrigatoria disciplina = new DisciplinaObrigatoria("DCC025", "Orientação a Objetos", null, null, 4);

            ValidadorSimples simples = new ValidadorSimples(disciplina);
            Aluno aluno = new Aluno("Alex", "202400001");
            aluno.getHistorico().put(disciplina, 60);

            assertTrue(simples.validar(aluno));
        }
        catch (Exception e) {
            fail("Não deveria ter exceção: " + e.getMessage());
        }

    }

    @Test
    public void testDisciplinaInvalida() {

        try {
            DisciplinaObrigatoria disciplina = new DisciplinaObrigatoria("DCC025", "Orientação a Objetos", null, null, 4);

            ValidadorSimples simples = new ValidadorSimples(disciplina);
            Aluno aluno = new Aluno("Alex", "202400001");
            aluno.getHistorico().put(disciplina, 59);

            assertFalse(simples.validar(aluno));
        }
        catch (Exception e) {
            fail("Não deveria ter exceção: " + e.getMessage());
        }

    }
}
