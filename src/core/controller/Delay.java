/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Flight;
import core.model.storage.Storage;

public class Delay {

    public static Response addDelay(String flightId, String hours, String minutes) {
        Flight flight = null;
        int hour, minute;
        if (hours.equals("")) {
            return new Response("Error: select an hour for delay duration", Status.BAD_REQUEST);
        }

        if (minutes.equals("")) {
            return new Response("Error: select an minute for delay duration.", Status.BAD_REQUEST);
        }
        try {
           hour = Integer.parseInt(hours);
        } catch (Exception e) {
             return new Response("Error: select an number for a hour for delay duration.", Status.BAD_REQUEST);
        }
        try {
           minute = Integer.parseInt(minutes);
        } catch (Exception e) {
             return new Response("Error: select an number for a minute for delay duration.", Status.BAD_REQUEST);
        }
        for (Flight f : Storage.getInstance().getFlights()) {
            if (flightId.equals(f.getId())) {
                flight = f;
            }
        }

        try {
            flight.delay(hour, minute);
            return new Response("Delay added",Status.OK);
        } catch (Exception e) {
            return new Response("Error: cant add delay.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
