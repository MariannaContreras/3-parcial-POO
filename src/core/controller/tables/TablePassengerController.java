/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.tables;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Passenger;
import core.model.storage.Storage;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gabyy
 */
public class TablePassengerController {

    public static Response updateTable(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            Storage storage = Storage.getInstance();
            ArrayList<Passenger> passengers = storage.getPassengers();

            if (passengers == null || passengers.isEmpty()) {
                return new Response("Error: The list is empty.", Status.NO_CONTENT, passengers.clone());
            }

            passengers.sort(Comparator.comparingLong(Passenger::getId));

            for (Passenger passenger : passengers) {
                model.addRow(new Object[]{
                    passenger.getId(),
                    passenger.getFirstname(),
                    passenger.getBirthDate(),
                    passenger.calculateAge(),
                    passenger.generateFullPhone(),
                    passenger.getCountry(),
                    passenger.getNumFlights()
                });
            }
            return new Response("Refresh OK",Status.OK, passengers.clone());
        }
        catch(Exception e){
            return new Response ("Error: inserting in the table.",Status.INTERNAL_SERVER_ERROR);
        }

    }

}
