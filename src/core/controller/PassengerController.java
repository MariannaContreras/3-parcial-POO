package core.controller;

import core.controller.utils.Create;
import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Passenger;
import core.model.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class PassengerController implements Create {

    @Override
    public Response create(Object... args) {
        if (args.length < 9) {
            return new Response("Error: Missing parameters.", Status.BAD_REQUEST);
        }

        String idTxt = args[0].toString();
        String firstN = args[1].toString();
        String lastN = args[2].toString();
        String yearTxt = args[3].toString();
        String monthTxt = args[4].toString();
        String dayTxt = args[5].toString();
        String phoneCTxt = args[6].toString();
        String phoneTxt = args[7].toString();
        String countryTxt = args[8].toString();

        try {
            long id, phone;
            int month, day, year, phoneC;
            LocalDate birth;

            Storage passengerStorage = Storage.getInstance();
            ArrayList<Passenger> passengers = passengerStorage.getPassengers();

            if (idTxt.isEmpty()) return new Response("Error: ID must not be empty.", Status.BAD_REQUEST);
            if (firstN.isEmpty()) return new Response("Error: First name must not be empty.", Status.BAD_REQUEST);
            if (lastN.isEmpty()) return new Response("Error: Last name must not be empty.", Status.BAD_REQUEST);
            if (yearTxt.isEmpty()) return new Response("Error: Year must not be empty.", Status.BAD_REQUEST);
            if (monthTxt.isEmpty()) return new Response("Error: Month must not be empty.", Status.BAD_REQUEST);
            if (dayTxt.isEmpty()) return new Response("Error: Day must not be empty.", Status.BAD_REQUEST);
            if (phoneCTxt.isEmpty()) return new Response("Error: Phone code must not be empty.", Status.BAD_REQUEST);
            if (phoneTxt.isEmpty()) return new Response("Error: Phone number must not be empty.", Status.BAD_REQUEST);
            if (countryTxt.isEmpty()) return new Response("Error: Country must not be empty.", Status.BAD_REQUEST);

            if (idTxt.length() > 15) return new Response("Error: ID must be less than 15 characters.", Status.BAD_REQUEST);

            try {
                id = Long.parseLong(idTxt);
                if (id <= 0) return new Response("Error: ID must be greater than 0.", Status.BAD_REQUEST);
            } catch (NumberFormatException e) {
                return new Response("Error: ID must be a number.", Status.BAD_REQUEST);
            }

            for (Passenger p : passengers) {
                if (p.getId() == id) {
                    return new Response("Error: ID already exists.", Status.BAD_REQUEST);
                }
            }

            try {
                year = Integer.parseInt(yearTxt);
                if (year <= 0) return new Response("Error: Year must be greater than 0.", Status.BAD_REQUEST);
            } catch (NumberFormatException e) {
                return new Response("Error: Year must be a number.", Status.BAD_REQUEST);
            }

            try {
                month = Integer.parseInt(monthTxt);
                if (month <= 0 || month > 12) {
                    return new Response("Error: Month must be in range 1–12.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Error: Month must be a number.", Status.BAD_REQUEST);
            }

            try {
                day = Integer.parseInt(dayTxt);
                if (day <= 0 || day > 31) {
                    return new Response("Error: Day must be in range 1–31.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Error: Day must be a number.", Status.BAD_REQUEST);
            }

            try {
                birth = LocalDate.of(year, month, day);
            } catch (Exception e) {
                return new Response("Error: Invalid date.", Status.BAD_REQUEST);
            }

            try {
                phoneC = Integer.parseInt(phoneCTxt);
                if (phoneC <= 0) return new Response("Error: Phone code must be greater than 0.", Status.BAD_REQUEST);
            } catch (NumberFormatException e) {
                return new Response("Error: Phone code must be a number.", Status.BAD_REQUEST);
            }

            try {
                phone = Long.parseLong(phoneTxt);
                if (phone <= 0) return new Response("Error: Phone number must be greater than 0.", Status.BAD_REQUEST);
                if (phoneTxt.length() > 15) return new Response("Error: Phone number must be less than 15 characters.", Status.BAD_REQUEST);
            } catch (NumberFormatException e) {
                return new Response("Error: Phone number must be a number.", Status.BAD_REQUEST);
            }

            Passenger passenger = new Passenger(id, firstN, lastN, birth, phoneC, phone, countryTxt);
            passengerStorage.addPassenger(passenger);
            return new Response("Passenger Register OK.", Status.OK);

        } catch (Exception e) {
            return new Response("Error: Unexpected failure. Ensure all numeric fields are correct.", Status.BAD_REQUEST);
        }
    }
}
