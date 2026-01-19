import java.io.Serializable;
import java.util.Objects;

public class Ksiazka implements Serializable {
    private String tytul;  // Pole 1: tytuł książki
    private String autor;  // Pole 2: autor książki

    public Ksiazka(String tytul, String autor) {
        this.tytul = tytul;
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Ksiazka{tytul='" + tytul + "', autor='" + autor + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ksiazka ksiazka = (Ksiazka) o;
        return Objects.equals(tytul, ksiazka.tytul) &&
                Objects.equals(autor, ksiazka.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tytul, autor);
    }
}