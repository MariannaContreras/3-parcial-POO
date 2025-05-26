/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.tables;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Location;
import core.model.storage.Storage;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gabyy
 */
public class TableLocationController {
    public static Response updateLocationList(DefaultTableModel model) {
        try {
            model.setRowCount(0); // Limpiar el modelo
            Storage locationStorage = Storage.getInstance();
            ArrayList<Location> locations = locationStorage.getLocations();

            if (locations == null || locations.isEmpty()) {
                return new Response("The list is empty.", Status.NO_CONTENT, locations.clone());
            }

            // Ordenar por ID ascendente
            locations.sort(Comparator.comparing(Location::getAirportId));

            for (Location location : locations) {
                model.addRow(new Object[]{
                    location.getAirportId(),
                    location.getAirportName(),
                    location.getAirportCity(),
                    location.getAirportCountry(),
                });
            }

            return new Response("Refresh OK", Status.OK, locations.clone());
        } catch (Exception e) {
            return new Response("Error: inserting in the table.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
