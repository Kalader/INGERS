package com.example.civicsense;

import java.io.Serializable;

public class Segnalazione implements Serializable {

    private static final long serialVersionUID = 1L;

    public String tipo;
    public String locazione;
    public String email;
    public String descrizione;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setLocazione(String locazione) {
        this.locazione = locazione;
    }

    public Segnalazione(String email, String tipo, String descrizione, String locazione) {
        setEmail(email);
        setTipo(tipo);
        setDescrizione(descrizione);
        setLocazione(locazione);
    }

    public Segnalazione(){}
}
