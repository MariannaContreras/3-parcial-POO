/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model.storage;

import core.model.Flight;
import core.model.Location;
import core.model.Passenger;
import core.model.Plane;
import java.util.ArrayList;

public class Storage {

    
    private static Storage instance;
    
    private ArrayList<Passenger> passengers;
    private ArrayList<Location> locations;
    private ArrayList<Flight> flights;
    private ArrayList<Plane> planes;
    
    private Storage(){
        this.flights = new  ArrayList<>();
        this.passengers = new ArrayList<>(); // Initialized list
        this.locations = new ArrayList<>(); // Initialized list
        this.planes = new ArrayList<>(); // Initialized list
    }

    public static Storage getInstance(){
        if (instance == null){
            instance = new Storage();
        }
        return instance;
    }

    public boolean addPassenger(Passenger passenger){
        if (passenger != null){
            for(Passenger p : this.passengers){
                if (p.getId() == passenger.getId()){
                    return false;
                }
            }
            this.passengers.add(passenger);
        }
        return false;
    }
    public boolean addLocation(Location location){
        if (location != null){
            for(Location l : this.locations){
                if (l.getAirportId().equals(location.getAirportId())){
                    return false;
                }
            }
            this.locations.add(location);
        }
        return false;
    }
    public boolean addFlight(Flight flight){
        if (flight != null){
            for(Flight f : this.flights){
                if (f.getId().equals(flight.getId())){
                    return false;
                }
            }
            this.flights.add(flight);
        }
        return false;
    }
    public boolean addPlane(Plane plane){
        if (plane != null){
            for(Plane p : this.planes){
                if (p.getId().equals(plane.getId())){
                    return false;
                }
            }
            this.planes.add(plane);
        }
        return false;
    }

    public ArrayList<Passenger> getPassengers() {
        return this.passengers;
    }
    public ArrayList<Location> getLocations() {
        return this.locations;
    }
    public ArrayList<Flight> getFlights() {
        return this.flights;
    }
    public ArrayList<Plane> getPlanes() {
        return this.planes;
    }
    
}