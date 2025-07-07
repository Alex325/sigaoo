package com.alex.dcc025;

import java.util.HashMap;
import java.util.Map;

public class Historico {
    private Map<Disciplina, Integer> historico = new HashMap<>();

    public Historico(Map<Disciplina, Integer> historico) {

        this.historico = historico;

    }

    public Map<Disciplina, Integer> getHistorico() {
        return this.historico;
    }
}
