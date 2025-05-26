/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.tables;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Flight;
import core.model.storage.Storage;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gabyy
 */
public class TableFlightsController {

    public static Response updateFlightList(DefaultTableModel model) {
        try {
            model.setRowCount(0); // Limpiar el modelo
            Storage flightStorage = Storage.getInstance();
            ArrayList<Flight> flights = flightStorage.getFlights();
            if (flights == null || flights.isEmpty()) {
                return new Response("The list is empty.", Status.NO_CONTENT, flights.clone());
            }

            // Ordenar por Fecha de salida ascendente
            flights.sort(Comparator.comparing(Flight::getDepartureDate));
            for (Flight flight : flights) {
                if (flight.getScaleLocation()!= null) {
                    model.addRow(new Object[]{
                    flight.getId(),
                    flight.getDepartureLocation().getAirportId(),
                    flight.getArrivalLocation().getAirportId(),
                    flight.getScaleLocation().getAirportId(),
                    flight.getDepartureDate(),
                    flight.calculateArrivalDate(),
                    flight.getPlane().getId(),
                    flight.getNumPassengers(),});
                }else{
                    model.addRow(new Object[]{
                    flight.getId(),
                    flight.getDepartureLocation().getAirportId(),
                    flight.getArrivalLocation().getAirportId(),
                    null,
                    flight.getDepartureDate(),
                    flight.calculateArrivalDate(),
                    flight.getPlane().getId(),
                    flight.getNumPassengers(),});
                }
            }

            return new Response("Refresh OK", Status.OK, flights.clone());
        } catch (Exception e) {
            return new Response("Error: inserting in the table.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
