/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.model;

import java.io.File;

/**
 *
 * @author Yosua
 */
public class Movie {
    private String name;
    private String genre;
    private String duration;
    private String director;
    private String trailerurl;
    private File poster;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTrailerurl() {
        return trailerurl;
    }

    public void setTrailerurl(String trailerurl) {
        this.trailerurl = trailerurl;
    }

    public File getPoster() {
        return poster;
    }

    public void setPoster(File poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Movie{" + "name=" + name + ", genre=" + genre + ", duration=" + duration + ", director=" + director + ", trailerurl=" + trailerurl + ", poster=" + poster + '}';
    }
    
    
}
