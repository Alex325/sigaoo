package com.alex.dcc025.turma;

import org.junit.jupiter.api.Test;

import com.alex.dcc025.exceptions.horario.DiaInvalidoException;
import com.alex.dcc025.exceptions.horario.FormatoHoraInvalidoException;

import static org.junit.jupiter.api.Assertions.*;


public class HorarioTest {

    @Test
    public void testHorarioValido() {

        assertDoesNotThrow(() -> { new Horario(1, "08:00-10:00"); });

    }
    @Test
    public void testHorarioDiaInvalido() {
        assertThrows(DiaInvalidoException.class, () -> {
            new Horario(8, "08:00-10:00");
        });
        assertThrows(DiaInvalidoException.class, () -> {
            new Horario(0, "08:00-10:00");
        });
    }
    @Test
    public void testFormatoHorarioInvalido() {
        assertThrows(FormatoHoraInvalidoException.class, () -> {
            new Horario(1, "25:00-26:00");
        });
        assertThrows(FormatoHoraInvalidoException.class, () -> {
            new Horario(1, "08:00-10:60");
        });
        assertThrows(FormatoHoraInvalidoException.class, () -> {
            new Horario(1, "08:00-10:00-12:00");
        });
    }
    @Test
    public void testHorarioConflitante() {
        try {
            Horario horario1 = new Horario(1, "08:00-10:00");
            Horario horario2 = new Horario(1, "10:00-12:00");
            assertFalse(horario1.horarioConflitante(horario2));
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testHorarioNaoConflitante() {
        try {
            Horario horario1 = new Horario(1, "08:00-10:00");
            Horario horario2 = new Horario(1, "10:00-12:00");
            assertFalse(horario1.horarioConflitante(horario2));
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testHorarioLegivel() {
        try {
            Horario horario = new Horario(1, "08:00-10:00");
            String horarioLegivel = horario.horarioLegivel();
            assertEquals("Domingo às 08:00-10:00", horarioLegivel);
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }
}
