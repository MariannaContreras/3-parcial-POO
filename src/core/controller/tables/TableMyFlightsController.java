/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.tables;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Flight;
import core.model.Passenger;

import core.model.storage.Storage;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gabyy
 */
public class TableMyFlightsController {

    public static Response updateTable(DefaultTableModel model, String idTxt) {
        try {
            model.setRowCount(0);
            long id = Long.parseLong(idTxt);
            Passenger pas = null;
            for (Passenger p : Storage.getInstance().getPassengers()) {
                if (p.getId() == id) {
                    pas = p;
                    break;
                }
            }

            if (pas != null) {
                // Obtener solo los vuelos del pasajero
                ArrayList<Flight> flightsOfPassenger = new ArrayList<>();
                for (Flight f : Storage.getInstance().getFlights()) {
                    if (f.getPassengers().contains(pas)) {
                        flightsOfPassenger.add(f);
                    }
                }

                // Ordenar por ID
                flightsOfPassenger.sort(Comparator.comparing(Flight::getId));

                for (Flight f : flightsOfPassenger) {
                    model.addRow(new Object[]{
                        f.getId(),
                        f.getDepartureDate(),
                        f.calculateArrivalDate()
                    });
                }

                return new Response("Refresh OK", Status.OK, flightsOfPassenger.clone());
            } else {
                return new Response("Error: Passenger not found.", Status.NOT_FOUND, null);
            }

        } catch (Exception e) {
            return new Response("Error: inserting in the table.", Status.INTERNAL_SERVER_ERROR, null);
        }
    }

}
