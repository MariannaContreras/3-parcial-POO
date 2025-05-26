/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.utils;

import core.model.Location;
import core.model.Plane;
import core.model.storage.Storage;
import java.lang.reflect.*;
import java.time.*;
import org.json.JSONObject;

public class JsonConverter {

    public static <T> T convertToObject(JSONObject json, Class<T> clazz) {
        try {
            Constructor<?>[] constructors = clazz.getConstructors();

            for (Constructor<?> constructor : constructors) {
                Class<?>[] paramTypes = constructor.getParameterTypes();
                Object[] args = new Object[paramTypes.length];

                Parameter[] parameters = constructor.getParameters();
                for (int i = 0; i < paramTypes.length; i++) {
                    String name = parameters[i].getName(); // Requiere -parameters en compilación
                    Class<?> type = paramTypes[i];

                    if (type == String.class) {
                        args[i] = json.getString(name);
                    } else if (type == int.class || type == Integer.class) {
                        args[i] = json.getInt(name);
                    } else if (type == long.class || type == Long.class) {
                        args[i] = json.getLong(name);
                    } else if (type == double.class || type == Double.class) {
                        args[i] = json.getDouble(name);
                    } else if (type == LocalDate.class) {
                        args[i] = LocalDate.parse(json.getString(name));
                    } else if (type == LocalDateTime.class) {
                        args[i] = LocalDateTime.parse(json.getString(name));
                    } else if (type == Plane.class) {
                        String planeId = json.getString(name);
                        args[i] = Storage.getInstance().getPlanes()
                                .stream().filter(p -> p.getId().equals(planeId)).findFirst().orElse(null);
                    } else if (type == Location.class) {
                        // Permite null (ej. scaleLocation)
                        String locId = json.optString(name, null);
                        if (locId != null && !locId.equals("null")) {
                            args[i] = Storage.getInstance().getLocations()
                                    .stream().filter(l -> l.getAirportId().equals(locId)).findFirst().orElse(null);
                        } else {
                            args[i] = null;
                        }
                    } else {
                        args[i] = null;
                    }
                }

                return (T) constructor.newInstance(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
