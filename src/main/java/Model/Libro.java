package Model;

import java.sql.Date;

public class Libro {
    private int idLibro;
    private String titulo;
    private int idAutor; //FK
    private Long year;
    private boolean disponibilidad;

    public Libro(String titulo, int idAutor, Long year) {
        this.titulo = titulo;
        this.idAutor = idAutor;
        this.year = year;
        this.disponibilidad = true;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public Long getYear() {
        return this.year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "idLibro=" + idLibro +
                ", titulo='" + titulo + '\'' +
                ", idAutor=" + idAutor +
                ", year=" + year +
                ", disponibilidad=" + disponibilidad +
                '}';
    }
}
