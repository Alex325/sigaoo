package com.alex.dcc025;

import java.util.Map;

public class Aluno {

    Historico historico;

    public Map<Disciplina, Integer> getHistorico() {
        return this.historico.getHistorico();
    }
}
