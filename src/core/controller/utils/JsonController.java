/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.swing.JComboBox;


public class JsonController {

    public static Response readJson(String archivo, JComboBox select,JSONArray guardado) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get("./json/" + archivo + ".json")));
            select.removeAllItems();
            ArrayList<Object> a = new ArrayList<>();
            ArrayList<String> selec = new ArrayList<>();
            guardado.clear();
            // Convertir a JSONArray
            JSONArray jsons = new JSONArray(contenido);
            guardado.putAll(jsons);
            Map<String, Object> jsonP = jsons.getJSONObject(0).toMap();
            Set<String> clavess = jsonP.keySet();
            ArrayList<String> claves = new ArrayList<>();
            for (String clave: clavess) {
                claves.add(clave);
            }
            

            for (int i = 0; i < jsons.length(); i++) {
                JSONObject json = jsons.getJSONObject(i);
                try{
                    a.add(json.get("id"));
                }catch(Exception e){
                    a.add(json.get("airportId"));
                }
            }
            for (Object obj : a ) {
                selec.add(obj.toString());
            }
            Collections.sort(selec);
            for(String items : selec){
                select.addItem(items);
            }
            
            return new Response("Json loaded", Status.OK);
        } catch (IOException e) {
            return new Response("Error: reading JSON: " + archivo, Status.INTERNAL_SERVER_ERROR);
        } catch (org.json.JSONException e) {
            return new Response("Error: processing JSON: " + archivo, Status.INTERNAL_SERVER_ERROR);
        }
    }
}
