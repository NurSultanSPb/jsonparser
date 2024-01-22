package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class Ticket implements Comparable<Ticket> {

    private String origin;
    private String origin_name;
    private String destination;
    private String destination_name;
    private String departure_date;
    private String departure_time;
    private String arrival_date;
    private String arrival_time;
    private String carrier;
    private byte stops;
    private double price;

    @SneakyThrows
    @Override
    @JsonIgnore
    public int compareTo(Ticket o) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");

        Date departureDate1 = simpleDateFormat.parse(departure_date + " " + departure_time);
        Date arrivalDate1 = simpleDateFormat.parse(arrival_date + " " + arrival_time);

        Date departureDate2 = simpleDateFormat.parse(o.getDeparture_date() + " " + o.getDeparture_time());
        Date arrivalDate2 = simpleDateFormat.parse(o.getArrival_date() + " " + o.getArrival_time());

        Long duration1 = arrivalDate1.getTime() - departureDate1.getTime();
        Long duration2 = arrivalDate2.getTime() - departureDate2.getTime();

        return (duration1 > duration2) ? 1 : (duration1 < duration2) ? -1 : 0;
    }
}
