package org.mce.gestionedeleghe.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Delega.
 */
@Document(collection = "T_DELEGA")
public class Delega implements Serializable {

    @Id
    private String id;

    @Field("nome")
    private String nome;

    @Field("cognome")
    private String cognome;

    @Field("codice_fiscale")
    private String codiceFiscale;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Delega delega = (Delega) o;

        if ( ! Objects.equals(id, delega.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Delega{" +
                "id=" + id +
                ", nome='" + nome + "'" +
                ", cognome='" + cognome + "'" +
                ", codiceFiscale='" + codiceFiscale + "'" +
                '}';
    }
}
