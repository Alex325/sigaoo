package com.alex.dcc025.validadores;

import org.junit.jupiter.api.Test;

import com.alex.dcc025.aluno.Aluno;
import com.alex.dcc025.disciplinas.Disciplina;
import com.alex.dcc025.disciplinas.DisciplinaObrigatoria;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ValidadorANDTest {

    @Test
    public void testTodosCumpridos() {

        try {
            DisciplinaObrigatoria disciplina1 = new DisciplinaObrigatoria("DCC026", "Estruturas de Dados", null, null, 4);
            DisciplinaObrigatoria disciplina2 = new DisciplinaObrigatoria("DCC027", "Algoritmos II", null, null, 6);

            List<Disciplina> preRequisitos = new ArrayList<>();
            preRequisitos.add(disciplina1);
            preRequisitos.add(disciplina2);

            ValidadorAND validadorAND = new ValidadorAND(preRequisitos);

            Aluno aluno = new Aluno("Alex", "202400001");
            aluno.getHistorico().put(disciplina1, 60);
            aluno.getHistorico().put(disciplina2, 60);

            assertTrue(validadorAND.validar(aluno));

        } catch (Exception e) {
            fail("Não deveria ter exceção: " + e.getMessage());
        }
    }

    @Test
    public void testUmNaoCumprido() {
        try {
            DisciplinaObrigatoria disciplina1 = new DisciplinaObrigatoria("DCC026", "Estruturas de Dados", null, null, 4);
            DisciplinaObrigatoria disciplina2 = new DisciplinaObrigatoria("DCC027", "Algoritmos II", null, null, 6);

            List<Disciplina> preRequisitos = new ArrayList<>();
            preRequisitos.add(disciplina1);
            preRequisitos.add(disciplina2);

            ValidadorAND validadorAND = new ValidadorAND(preRequisitos);

            Aluno aluno = new Aluno("Alex", "202400001");
            aluno.getHistorico().put(disciplina1, 60);
            aluno.getHistorico().put(disciplina2, 59);

            assertFalse(validadorAND.validar(aluno));

        } catch (Exception e) {
            fail("Não deveria ter exceção: " + e.getMessage());
        }
    }

}
