package com.alex.dcc025.disciplinas;

import org.junit.jupiter.api.Test;

import com.alex.dcc025.exceptions.disciplina.CodigoDisciplinaInvalidoException;

import static org.junit.jupiter.api.Assertions.*;

public class DisciplinaTest {
    @Test
    public void testCodigoValido() {
        
        assertDoesNotThrow(() -> { new DisciplinaObrigatoria("DCC025", "Orientação a Objetos", null, null, 4); });

    }

    @Test
    public void testCodigoInvalido() {
        assertThrows(CodigoDisciplinaInvalidoException.class, () -> { new DisciplinaObrigatoria("DCC2", "Orientação a Objetos", null, null, 4); });
    }
}
