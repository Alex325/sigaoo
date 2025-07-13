package com.alex.dcc025.turma;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.alex.dcc025.exceptions.horario.DiaInvalidoException;
import com.alex.dcc025.exceptions.horario.FormatoHoraInvalidoException;
import com.alex.dcc025.exceptions.horario.HoraInvalidaException;
import com.alex.dcc025.exceptions.matricula.IntervaloHoraInvalidoException;

public class Horario {
    private final int dia;
    private final int[] horario;

    private static final Map<Integer, String> dicionario;

    static {
        Map<Integer, String> temporario = new HashMap<>();
        temporario.put(1, "Domingo");
        temporario.put(2, "Segunda-feira");
        temporario.put(3, "Terça-feira");
        temporario.put(4, "Quarta-feira");
        temporario.put(5, "Quinta-feira");
        temporario.put(6, "Sexta-feira");
        temporario.put(7, "Sábado");
        dicionario = Collections.unmodifiableMap(temporario);
    }

    public Horario(int dia, String horario) throws HoraInvalidaException, DiaInvalidoException {

        if (dia < 1 || dia > 7) throw new DiaInvalidoException("Dia inválido: " + dia);

        this.dia = dia;

        if (!formatoValido(horario)) throw new FormatoHoraInvalidoException("Formato de horário inválido: " + horario);

        int[] horarioIntervalo = converterHorarioParaMinutos(horario);

        if (!horarioValido(horarioIntervalo)) throw new IntervaloHoraInvalidoException("Horário inválido: " + horario);

        this.horario = horarioIntervalo;

    }

    public final String horarioLegivel() {
        StringBuilder horarioLegivel = new StringBuilder();
        horarioLegivel.append(dicionario.get(dia));
        horarioLegivel.append(" às ");
        horarioLegivel.append(horarioString());
        return horarioLegivel.toString();
    }

    public boolean horarioConflitante(Horario outroHorario) {

        if (this.dia != outroHorario.dia) return false;

        return this.horario[1] > outroHorario.horario[0] && this.horario[0] < outroHorario.horario[1];
    }

    private boolean horarioValido(int[] horario) {
        int inicio = horario[0];
        int fim = horario[1];
        return inicio < fim;
    }

    private boolean formatoValido(String horario) {
        return horario.matches("^([01][0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

    private int[] converterHorarioParaMinutos(String horario) {

        String[] partes = horario.split("-");
        String[] inicio = partes[0].split(":");
        String[] fim = partes[1].split(":");

        int inicioMinutos = Integer.parseInt(inicio[0]) * 60 + Integer.parseInt(inicio[1]);
        int fimMinutos = Integer.parseInt(fim[0]) * 60 + Integer.parseInt(fim[1]);

        return new int[]{inicioMinutos, fimMinutos};
    }

    private String horarioString() {
        String inicio = String.format("%02d:%02d", horario[0] / 60, horario[0] % 60);
        String fim = String.format("%02d:%02d", horario[1] / 60, horario[1] % 60);
        return inicio + "-" + fim;
    }
}
