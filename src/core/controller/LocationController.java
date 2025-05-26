package core.controller;

import core.controller.utils.Create;
import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Location;
import core.model.storage.Storage;

import java.util.ArrayList;

public class LocationController implements Create {

    @Override
    public Response create(Object... args) {
        if (args.length < 6) {
            return new Response("Error: Missing parameters", Status.BAD_REQUEST);
        }

        String airportId = args[0].toString();
        String airportName = args[1].toString();
        String airportCity = args[2].toString();
        String airportCountry = args[3].toString();
        String airportLatitude = args[4].toString();
        String airportLongitude = args[5].toString();

        double airportLat;
        double airportLong;

        Storage locationStorage = Storage.getInstance();
        ArrayList<Location> locations = locationStorage.getLocations();

        try {
            if (airportId.isEmpty()) return new Response("Error: ID must not be empty.", Status.BAD_REQUEST);
            if (airportName.isEmpty()) return new Response("Error: Name must not be empty.", Status.BAD_REQUEST);
            if (airportCity.isEmpty()) return new Response("Error: City must not be empty.", Status.BAD_REQUEST);
            if (airportCountry.isEmpty()) return new Response("Error: Country must not be empty.", Status.BAD_REQUEST);
            if (airportLatitude.isEmpty()) return new Response("Error: Latitude must not be empty.", Status.BAD_REQUEST);
            if (airportLongitude.isEmpty()) return new Response("Error: Longitude must not be empty.", Status.BAD_REQUEST);

            for (Location location : locations) {
                if (location.getAirportId().equals(airportId)) {
                    return new Response("Error: ID already exists.", Status.BAD_REQUEST);
                }
            }

            if (airportId.length() != 3) return new Response("Error: ID must have 3 uppercase letters.", Status.BAD_REQUEST);
            if (!airportId.equals(airportId.toUpperCase())) return new Response("Error: ID must be uppercase.", Status.BAD_REQUEST);

            try {
                airportLat = Double.parseDouble(airportLatitude);
                if (airportLat < -90 || airportLat > 90) {
                    return new Response("Error: Latitude must be in range (-90, 90).", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Error: Latitude must be numeric.", Status.BAD_REQUEST);
            }

            try {
                airportLong = Double.parseDouble(airportLongitude);
                if (airportLong < -180 || airportLong > 180) {
                    return new Response("Error: Longitude must be in range (-180, 180).", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Error: Longitude must be numeric.", Status.BAD_REQUEST);
            }

            Location newLocation = new Location(airportId, airportName, airportCity, airportCountry, airportLat, airportLong);
            locationStorage.addLocation(newLocation);

            return new Response("Location added successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Error: Bad Request", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
