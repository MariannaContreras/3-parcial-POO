/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.utils;

import core.model.Flight;
import core.model.Location;
import core.model.Passenger;
import core.model.Plane;
import core.model.storage.Storage;
import org.json.JSONArray;
import org.json.JSONObject;

public class StorageAdder {

    public static Response storageAdd(JSONArray objeto, String archivo) {
        Storage storage = Storage.getInstance();
        try {
            if (archivo.equalsIgnoreCase("passengers")) {

                for (int i = 0; i < objeto.length(); i++) {
                    JSONObject json = objeto.getJSONObject(i);
                    Passenger pass = JsonConverter.convertToObject(json, Passenger.class);
                    storage.addPassenger(pass);
                }

            }
            else if (archivo.equalsIgnoreCase("flights")) {

                for (int i = 0; i < objeto.length(); i++) {
                    JSONObject json = objeto.getJSONObject(i);
                    Flight fli = JsonConverter.convertToObject(json, Flight.class);

                    storage.addFlight(fli);
                }

            }
            else if (archivo.equalsIgnoreCase("locations")) {

                for (int i = 0; i < objeto.length(); i++) {
                    JSONObject json = objeto.getJSONObject(i);
                    Location loc = JsonConverter.convertToObject(json, Location.class);
                    storage.addLocation(loc);
                }

            }
            else if (archivo.equalsIgnoreCase("planes")) {

                for (int i = 0; i < objeto.length(); i++) {
                    JSONObject json = objeto.getJSONObject(i);
                    Plane plane = JsonConverter.convertToObject(json, Plane.class);
                    storage.addPlane(plane);
                }

            }
            else{
            return new Response("Error: Storage no Charge",Status.INTERNAL_SERVER_ERROR);
            }
             
            return new Response("Storage complete",Status.INTERNAL_SERVER_ERROR);
        }catch(Exception e){
            return new Response("Error: Storage no Charge",Status.INTERNAL_SERVER_ERROR);
        }

    }
}
