package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Ticket;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Utility {

    private final String SOURCE_ROOT = "src/main/resources/tickets.json";

    public List<Ticket> getTickets() throws IOException {

        File file = new File(SOURCE_ROOT);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(file);
        List<Ticket> tickets = new ArrayList<>();

        for (JsonNode ticketNode : jsonNode.get("tickets")) {
            Ticket ticket = objectMapper.treeToValue(ticketNode, Ticket.class);
            tickets.add(ticket);
        }

        return tickets;
    }

    public String getMinValueOfTicketPerEachCompany(List<Ticket> tickets) {
        // все билеты
        // билеты одной авиакомпании
        // мапа с ключом авиакомпания и со значением время полета
        StringBuilder result = new StringBuilder();
        List<String> companies = tickets.stream().map(Ticket::getCarrier).distinct().toList();

        for (String company: companies) {
            Ticket minTicket = tickets.stream().filter(t -> t.getCarrier().equals(company)).min(Ticket::compareTo).get();
            result.append("Для авиакомпании " + company + " минимальная продолжительность полета составляет ");
            result.append(getHoursAndMinutes(minTicket));
            result.append("\n");
        }

        return result.toString();

    }

    public String getHoursAndMinutes (Ticket ticket1) {
        Date dateAndTimeOfDeparture1 = getDateAndTime(ticket1.getDeparture_date(), ticket1.getDeparture_time());
        Date dateAndTimeOfArrival1 = getDateAndTime(ticket1.getArrival_date(), ticket1.getArrival_time());

        long time = dateAndTimeOfArrival1.getTime() - dateAndTimeOfDeparture1.getTime();
        long l = (time / 1000) / 60;
        return l / 60 + " часов и " + l % 60 + " минут.";
    }

    public Date getDateAndTime(String date, String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date dateAndTime;
        try {
            dateAndTime = simpleDateFormat.parse(date + " " + time);
        } catch (ParseException parseException) {
            throw new RuntimeException(parseException);
        }
        return dateAndTime;
    }

    public double getDifferencePrice(List<Ticket> tickets) {
        double[] prices = tickets.stream().mapToDouble(Ticket::getPrice).toArray();
        double averagePrice = Arrays.stream(prices).average().orElse(0);
        int n = prices.length;
        double medianPrice = n % 2 == 0 ? ((prices[n / 2 - 1] + prices[n / 2]) / 2) : prices[n / 2];
        return Math.abs(averagePrice - medianPrice);
    }

}
