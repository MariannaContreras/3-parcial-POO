/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller;

import core.controller.utils.Create;
import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Flight;
import core.model.Location;
import core.model.Plane;
import core.model.storage.Storage;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class FlightController implements Create{
    @Override
    public Response create(Object... args) {
        Pattern pattern = Pattern.compile("^[A-z]{3}\\d{3}$");

        Location departureLoc = null;
        Location scaleLoc = null;
        Location arrivalLoc = null;
        Plane plane = null ;
        LocalDateTime departureDateTime;

        int hoursDurArrival;
        int minutesDurArrival;
        int hoursDurScale = 0;
        int minutesDurScale = 0;
        int deparYear;
        int deparMonth;
        int deparDay;
        int deparHour;
        int deparMinute;
        
        Storage storage = Storage.getInstance();

        ArrayList<Flight> flights = storage.getFlights();
        ArrayList<Plane> planes = storage.getPlanes();
        ArrayList<Location> departureLocationIdStorages = storage.getLocations();

        try {
            if (args[0].toString().equals("")) {
                return new Response("Error: ID must be not empty.", Status.BAD_REQUEST);
            }

            if (args[1].toString().equals("")) {
                return new Response("Error: select a plane ID.", Status.BAD_REQUEST);
            }

            if (args[2].toString().equals("")) {
                return new Response("Error: select a departure location ID.", Status.BAD_REQUEST);
            }

            if (args[3].toString().equals("")) {
                return new Response("Error: select a scale location ID.", Status.BAD_REQUEST);
            }

            if (args[4].toString().equals("")) {
                return new Response("Error: select an arrival location ID.", Status.BAD_REQUEST);
            }

            if (args[5].toString().equals("")) {
                return new Response("Error: Year must be not empty.", Status.BAD_REQUEST);
            }

            if (args[6].toString().equals("")) {
                return new Response("Error: select a departure month.", Status.BAD_REQUEST);
            }

            if (args[7].toString().equals("")) {
                return new Response("Error: select a departure day.", Status.BAD_REQUEST);
            }

            if (args[8].toString().equals("")) {
                return new Response("Error: select a departure hour.", Status.BAD_REQUEST);
            }

            if (args[9].toString().equals("")) {
                return new Response("Error: select a departure minute.", Status.BAD_REQUEST);
            }

            if (args[10].toString().equals("")) {
                return new Response("Error: select an hour for arrival duration", Status.BAD_REQUEST);
            }

            if (args[11].toString().equals("")) {
                return new Response("Error: select minute for arrival duration.", Status.BAD_REQUEST);
            }

            // Validar formato de IDs
            if (!pattern.matcher(args[0].toString()).matches()) {
                return new Response("Error: Invalid flight ID format found", Status.BAD_REQUEST);
            }

            for (Flight flight : flights) {
                if (flight.getId().equals(args[0].toString())) {
                    return new Response("Error: A flight with this ID is already registered.", Status.BAD_REQUEST);
                }
            }
            try {
                deparYear = Integer.parseInt(args[5].toString());
            } catch (NumberFormatException e) {
                return new Response("Error: The departure year must be numeric.", Status.BAD_REQUEST);
            }
            try {
                deparMonth = Integer.parseInt(args[6].toString());
            } catch (NumberFormatException e) {
                return new Response("Error: Please select a departure month.", Status.BAD_REQUEST);
            }
            try {
                deparDay = Integer.parseInt(args[7].toString());
            } catch (NumberFormatException e) {
                return new Response("Error: Please select a day.", Status.BAD_REQUEST);
            }
            try {
                deparHour = Integer.parseInt(args[8].toString());
            } catch (NumberFormatException e) {
                return new Response("Error: Please select a hour.", Status.BAD_REQUEST);
            }
            try {
                deparMinute = Integer.parseInt(args[9].toString());
            } catch (NumberFormatException e) {
                return new Response("Error: Please select a minute.", Status.BAD_REQUEST);
            }
            try {
                departureDateTime = LocalDateTime.of(deparYear, deparMonth, deparDay, deparHour, deparMinute);
            } catch (DateTimeException e) {
                return new Response("Error: Please select a valid date.", Status.BAD_REQUEST);
            }
            try {
                hoursDurArrival = Integer.parseInt(args[10].toString());
            } catch (NumberFormatException e) {
                return new Response("Error: The Arrival Hour must be numeric.", Status.BAD_REQUEST);
            }
            try {
                minutesDurArrival = Integer.parseInt(args[11].toString());
            } catch (NumberFormatException e) {
                return new Response("Error: The Arrival Minute must be numeric.", Status.BAD_REQUEST);
            }
            for (Plane plane1 : planes) {
                if (plane1.getId().equals(args[1].toString())) {
                    plane = plane1;
                }

            }

            if (plane == null) {
                return new Response("Error: A plane with this ID is not registered.", Status.BAD_REQUEST);
            }

            for (Location location : departureLocationIdStorages) {
                if (location.getAirportId().equals(args[2].toString())) {
                    departureLoc = location;
                }
                if (location.getAirportId().equals(args[3].toString())) {
                    scaleLoc = location;
                }
                if (location.getAirportId().equals(args[4].toString())) {
                    arrivalLoc = location;
                }
            }
            if (departureLoc == null) {
                return new Response("Error: Location with the departureLocation ID is not registered.", Status.BAD_REQUEST);
            }

            if (arrivalLoc == null) {
                return new Response("Error: Location with the arrivalLocation ID is not registered.", Status.BAD_REQUEST);
            }
            Flight flight;
            if (scaleLoc == null & args[3].toString().equals("Location")) {
                flight = new Flight(args[0].toString(), plane, departureLoc, arrivalLoc, departureDateTime, hoursDurArrival, minutesDurArrival);
                // validar que se siga el formato xxxyyy
                storage.addFlight(flight);
                if (flight.getPlane() != null) {
                    flight.getPlane().addFlight(flight);
                }
                return new Response("Plane added successfully", Status.OK);
            } else if (scaleLoc == null) {
                return new Response("Error: Location with the scaleLocation ID is not registered.", Status.BAD_REQUEST);
            } else {
                if (args[12].toString().equals("Hour")) {
                    return new Response("Error: select an hour for scale duration", Status.BAD_REQUEST);
                }

                if (args[13].toString().equals("Minute")) {
                    return new Response("Error: select minute for scale duration", Status.BAD_REQUEST);
                }
                try {
                    hoursDurScale = Integer.parseInt(args[12].toString());
                } catch (NumberFormatException e) {
                    return new Response("Error: The Scale Hour must be numeric.", Status.BAD_REQUEST);
                }
                try {
                    minutesDurScale = Integer.parseInt(args[13].toString());
                } catch (NumberFormatException e) {
                    return new Response("Error: The Scale Minute must be numeric.", Status.BAD_REQUEST);
                }
                flight = new Flight(args[0].toString(), plane, departureLoc, scaleLoc, arrivalLoc, departureDateTime, hoursDurArrival, minutesDurArrival, hoursDurScale, minutesDurArrival);
                storage.addFlight(flight);
                if (flight.getPlane() != null) {
                    flight.getPlane().addFlight(flight);
                }
                return new Response("Plane added successfully", Status.OK);
            }

        } catch (NumberFormatException e) {
            return new Response("Error: Must be numeric", Status.INTERNAL_SERVER_ERROR);
        }
    }

    
    
    
    
}
