import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookingService {
    private List<Train> trainList = new ArrayList<>();
    private List<Ticket> ticketList = new ArrayList<>();


    public BookingService(){
        trainList.add(new Train(101, "Rajdhani Express", "Delhi", "Nagpur", 100));
        trainList.add(new Train(102, "Shatabdi Express", "Mumbai", "Pune", 80));
        trainList.add(new Train(103, "Duronto Express", "Kolkata", "Bhubaneswar", 120));
        trainList.add(new Train(104, "Garib Rath", "Lucknow", "Delhi", 60));
        trainList.add(new Train(105, "Vande Bharat", "Chennai", "Bangalore", 90));
        trainList.add(new Train(106, "Tejas Express", "Ahmedabad", "Mumbai", 100));
    }
    public List<Train> searchTrain(String source, String destination){
        List<Train> res = new ArrayList<>();
        for (Train train:trainList){
            if (train.getSource().equalsIgnoreCase(source) && train.getDestination().equalsIgnoreCase(destination)){
                res.add(train);
            }
        }
        return res;
    }
    public Ticket bookTicket(User user, int trainId, int seatCount){
        for (Train train:trainList){
            if (train.getTrainId()==trainId){
                if (train.bookSeats(seatCount)){
                    Ticket ticket= new Ticket(user,train,seatCount);
                    ticketList.add(ticket);
                    return ticket;

                }
                else {
                    System.out.println("No enough seats available");
                    return null;
                }
            }
        }
        System.out.println("Train Id Not Found");
        return null;
    }

    public List<Ticket> getTicketByUser(User user) {
        List<Ticket> res = new ArrayList<>();

        for (Ticket ticket : ticketList) {
            if (ticket.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
                res.add(ticket);
            }
        }

        return res;
    }
    public boolean cancelTicket(int ticketId, User user) {
        Iterator<Ticket> iterator = ticketList.iterator(); // ✅ Fixed declaration

        while (iterator.hasNext()) {
            Ticket ticket = iterator.next();

            if (ticket.getTicketId() == ticketId &&
                    ticket.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {

                Train train = ticket.getTrain(); // ✅ Fixed =
                train.cancleSeats(ticket.getSeatBooked()); // ✅ Assumes this method exists
                iterator.remove();

                System.out.println("Ticket " + ticketId + " cancelled successfully");
                return true;
            }
        }

        System.out.println("Ticket not found");
        return false;
    }
    public void ListAllTrain(){
        System.out.println("List of All Trains: ");
        for (Train train:trainList){
            System.out.println(train);
        }
    }




}
