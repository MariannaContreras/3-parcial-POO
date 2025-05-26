package core.controller;

import core.controller.utils.Create;
import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Plane;
import core.model.storage.Storage;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class PlaneController implements Create {

    @Override
    public Response create(Object... args) {
        if (args.length < 5) {
            return new Response("Error: Missing parameters.", Status.BAD_REQUEST);
        }

        String id = args[0].toString();
        String brand = args[1].toString();
        String model = args[2].toString();
        String maxCapacity = args[3].toString();
        String airline = args[4].toString();

        Pattern ID_PATTERN = Pattern.compile("^[A-Z]{2}\\d{5}$");
        int maxCapacityInt;

        Storage planeStorage = Storage.getInstance();
        ArrayList<Plane> planes = planeStorage.getPlanes();

        try {
            if (id.isEmpty()) return new Response("Error: ID must not be empty.", Status.BAD_REQUEST);
            if (brand.isEmpty()) return new Response("Error: Brand must not be empty.", Status.BAD_REQUEST);
            if (model.isEmpty()) return new Response("Error: Model must not be empty.", Status.BAD_REQUEST);
            if (maxCapacity.isEmpty()) return new Response("Error: Max Capacity must not be empty.", Status.BAD_REQUEST);
            if (airline.isEmpty()) return new Response("Error: Airline must not be empty.", Status.BAD_REQUEST);

            for (Plane plane : planes) {
                if (plane.getId().equals(id)) {
                    return new Response("Error: Plane ID already exists.", Status.BAD_REQUEST);
                }
            }

            if (!ID_PATTERN.matcher(id).matches()) {
                return new Response("Error: Invalid plane ID format. Use format: AA12345", Status.BAD_REQUEST);
            }

            try {
                maxCapacityInt = Integer.parseInt(maxCapacity);
            } catch (NumberFormatException e) {
                return new Response("Error: Max Capacity must be numeric.", Status.BAD_REQUEST);
            }

            Plane newPlane = new Plane(id, brand, model, maxCapacityInt, airline);
            planeStorage.addPlane(newPlane);

            return new Response("Plane added successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Error: Unexpected failure during plane creation.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
