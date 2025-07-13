package com.alex.dcc025.turma;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.alex.dcc025.aluno.Aluno;
import com.alex.dcc025.disciplinas.Disciplina;
import com.alex.dcc025.disciplinas.DisciplinaObrigatoria;
import com.alex.dcc025.exceptions.matricula.TurmaCheiaException;

public class TurmaTest {
    @Test
    public void testMatricularAlunoComVaga() throws Exception {
        Disciplina disciplina = new DisciplinaObrigatoria("DCC024", "Programação Orientada a Objetos", null, null, 4);
        
        Turma turma = new Turma("A", disciplina, 1, new Horario(2, "08:00-12:00"));

        Aluno aluno = new Aluno("Eu", "202443122");

        assertDoesNotThrow(() -> turma.matricularAluno(aluno));
    }

    @Test
    public void testMatricularAlunoSemVaga() throws Exception {
        Disciplina disciplina = new DisciplinaObrigatoria("DCC024", "Programação Orientada a Objetos", null, null, 4);
        
        Turma turma = new Turma("A", disciplina, 1, new Horario(2, "08:00-12:00"));

        Aluno aluno1 = new Aluno("Eu", "202443122");
        Aluno aluno2 = new Aluno("Ele", "202444122");

        turma.matricularAluno(aluno1);

        assertThrows(TurmaCheiaException.class, () -> turma.matricularAluno(aluno2));
    }
}
