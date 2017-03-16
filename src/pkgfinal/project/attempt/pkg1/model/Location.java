/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgfinal.project.attempt.pkg1.model;
/**
 *
 * @author enric
 */
public class Location {
    
    String name;
    int numberOfTheater;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfTheater() {
        return numberOfTheater;
    }

    public void setNumberOfTheater(int numberOfTheater) {
        this.numberOfTheater = numberOfTheater;
    }

    @Override
    public String toString() {
        return "Location{" + "name=" + name + ", numberOfTheater=" + numberOfTheater + '}';
    }
    
    
    
}
