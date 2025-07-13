package com.alex.dcc025.aluno;

import org.junit.jupiter.api.Test;

import com.alex.dcc025.exceptions.matricula.FormatoMatriculaInvalidoException;

import static org.junit.jupiter.api.Assertions.*;

public class AlunoTest {

    @Test
    public void testMatriculaValida() {
        assertDoesNotThrow(() -> { new Aluno("João", "202300001"); });
    }

    @Test 
    public void testMatriculaInvalida() {
        assertThrows(FormatoMatriculaInvalidoException.class, () -> { new Aluno("João", "2023001"); });
        assertThrows(FormatoMatriculaInvalidoException.class, () -> { new Aluno("João", "2023A001"); });
        assertThrows(FormatoMatriculaInvalidoException.class, () -> { new Aluno("João", "2023AA0A1"); });
        assertThrows(FormatoMatriculaInvalidoException.class, () -> { new Aluno("João", "2023000A1"); });
    }
    
}
