/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Passenger;
import core.model.storage.Storage;
import java.time.LocalDate;

public class UpdateInfoController {
    public static Response updateInfo(long id, String firstN, String lastN, String yearTxt, String monthTxt, String dayTxt, String phoneCTxt, String phoneTxt, String countryTxt) {
        try {
            long phone;
            int month, day, year, phoneC;
            LocalDate birth;
            Storage passengerStorage = Storage.getInstance(); 

            if (firstN.equals("")) {
                return new Response("Error: First name must be not empty.", Status.BAD_REQUEST);
            }
            if (lastN.equals("")) {
                return new Response("Error: Last name must be not empty.", Status.BAD_REQUEST);
            }
            if (yearTxt.equals("")) {
                return new Response("Error: Year must be not empty.", Status.BAD_REQUEST);
            }
            if (monthTxt.equals("")) {
                return new Response("Error: Month must be not empty.", Status.BAD_REQUEST);
            }
            if (dayTxt.equals("")) {
                return new Response("Error: Day must be not empty.", Status.BAD_REQUEST);
            }
            if (phoneCTxt.equals("")) {
                return new Response("Error: Phone code must be not empty.", Status.BAD_REQUEST);
            }
            if (phoneTxt.equals("")) {
                return new Response("Error: Phone number must be not empty.", Status.BAD_REQUEST);
            }
            if (countryTxt.equals("")) {
                return new Response("Error: Country must be not empty.", Status.BAD_REQUEST);
            }
            try {
                year = Integer.parseInt(yearTxt);
            }catch (NumberFormatException e) {
                return new Response("Error: Year must be a number.", Status.BAD_REQUEST);
            }
            if (year <= 0){
                return new Response("Error: Year must be greater than 0.", Status.BAD_REQUEST);
            }
            try{
                month = Integer.parseInt(monthTxt);
            }catch (NumberFormatException e) {
                return new Response("Error: Month must be a number.", Status.BAD_REQUEST);
            }
            if (month <= 0 || month > 12){
                return new Response("Error: Month must be greater than 0 and less than 13.", Status.BAD_REQUEST);
            }
            try{
                day = Integer.parseInt(dayTxt);
            }catch (NumberFormatException e) {
                return new Response("Error: Day must be a number.", Status.BAD_REQUEST);
            }
            if (day <= 0 || day > 31){
                return new Response("Error: Day must be greater than 0 and less than 32.", Status.BAD_REQUEST);
            }
            try {
                birth = LocalDate.of(year, month, day);
            } catch (Exception e) {
                return new Response("Error: Invalid date.", Status.BAD_REQUEST);
            }
            try{
                phoneC = Integer.parseInt(phoneCTxt);
            }catch (NumberFormatException e) {
                return new Response("Error: Phone code must be a number.", Status.BAD_REQUEST);
            }
            if (phoneC <= 0){
                return new Response("Error: Phone code must be greater than 0.", Status.BAD_REQUEST);
            }
            try{
                phone = Long.parseLong(phoneTxt);
            }catch (NumberFormatException e) {
                return new Response("Error: Phone number must be a number.", Status.BAD_REQUEST);
            }
            if (phone <= 0){
                return new Response("Error: Phone number must be greater than 0.", Status.BAD_REQUEST);
            }
            if (phoneTxt.length() > 15){
                return new Response("Error: Phone number must be less than 15 characters.", Status.BAD_REQUEST);
            }
            for (Passenger p : passengerStorage.getPassengers()) {
                if (p.getId() == id) {
                    p.setBirthDate(birth);
                    p.setCountry(countryTxt);
                    p.setCountryPhoneCode(phoneC);
                    p.setFirstname(firstN);
                    p.setLastname(lastN);
                    p.setPhone(phone);
                }
            }
            return new Response("Info have been update.",Status.OK);
        } catch (Exception e) {
            return new Response("Error: El ID, el teléfono y la fecha de nacimiento deben ser números enteros.", Status.BAD_REQUEST);
        }
    }
}
