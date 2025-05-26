/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.tables;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Plane;
import core.model.storage.Storage;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gabby
 */
public class TablePlaneController {
        public static Response updateTable(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            Storage storage = Storage.getInstance();
            ArrayList<Plane> planes = storage.getPlanes();

            if (planes == null || planes.isEmpty()) {
                return new Response("Error: The list is empty.", Status.NO_CONTENT,planes.clone());
            }

            planes.sort(Comparator.comparing(Plane::getId));

            for (Plane plane : planes) {
                model.addRow(new Object[]{
                    plane.getId(),
                    plane.getBrand(),
                    plane.getModel(),
                    plane.getMaxCapacity(),
                    plane.getAirline(),
                    plane.getNumFlights()
                });
            }
            return new Response("Refresh OK",Status.OK,planes.clone());
        }
        catch(Exception e){
            return new Response ("Error: inserting in the table.",Status.INTERNAL_SERVER_ERROR);
        }

    }

}
