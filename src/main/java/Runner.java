import model.Ticket;
import util.Utility;

import java.io.IOException;
import java.util.List;

public class Runner {
    public static void main(String[] args) throws IOException {
        Utility utility = new Utility();
        List<Ticket> tickets = utility.getTickets();
        System.out.println(utility.getMinValueOfTicketPerEachCompany(tickets));
        System.out.println("Разница между средней ценой и медианой равна " + utility.getDifferencePrice(tickets) + " рублей.");
    }
}
