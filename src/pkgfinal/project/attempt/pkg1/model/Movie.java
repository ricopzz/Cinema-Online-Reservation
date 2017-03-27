/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.model;

import java.io.File;
import java.sql.Time;

/**
 *
 * @author Yosua
 */
public class Movie {
    private String name;
    private String genre1;
    private String genre2;
    private String genre3;
    private Time duration;
    private String director;
    private String trailerurl;
    private File poster;
    private double rating;

    public Movie(String name, String genre1, String genre2, String genre3, Time duration, String director, String trailerurl, File poster, double rating) {
        this.name = name;
        this.genre1 = genre1;
        this.genre2 = genre2;
        this.genre3 = genre3;
        this.duration = duration;
        this.director = director;
        this.trailerurl = trailerurl;
        this.poster = poster;
        this.rating = rating;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre1() {
        return genre1;
    }

    public void setGenre1(String genre) {
        this.genre1 = genre;
    }

    public String getGenre2() {
        return genre2;
    }

    public void setGenre2(String genre2) {
        this.genre2 = genre2;
    }

    public String getGenre3() {
        return genre3;
    }

    public void setGenre3(String genre3) {
        this.genre3 = genre3;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
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
        return "Movie{" + "name=" + name + ", genre=" + genre1 + ", duration=" + getDuration() + ", director=" + director + ", trailerurl=" + trailerurl + ", poster=" + poster + '}';
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    
    
}
