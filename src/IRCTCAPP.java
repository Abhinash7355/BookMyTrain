import java.util.List;
import java.util.Scanner;

public class IRCTCAPP {

    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final BookingService bookingService = new BookingService();

    public static void main(String[] args) {
        new IRCTCAPP().start();

    }
    public void start(){
        while (true){
            System.out.println("\n<........Welcome To IRCTC APP ....>");
            if (!userService.isLoogingIn()){
                System.out.println("1. Register..");
                System.out.println("2. Login..");
                System.out.println("3. Exit..");
                System.out.println("4. Enter Choice..");
                int choice = scanner.nextInt();
                switch (choice){
                    case 1 -> register();
                    case 2 -> login();
                    case 3 -> exitApp();
                    default -> System.out.println("Invalid choice..");
                }
            }else {
                showUserMenu();
            }
        }

    }
    public void register(){
        System.out.println("Enter username : ");
        String username = scanner.next();
        System.out.println("Enter password : ");
        String password = scanner.next();
        System.out.println("Enter Full Name : ");
        //scanner.nextInt();
        String fullName = scanner.nextLine();
        System.out.println("Enter contact : ");
        String contact = scanner.next();

        userService.registerUser(username,password,fullName,contact);




    }
    public void login(){
        System.out.println("Enter username : ");
        String username = scanner.next();
        System.out.println("Enter password : ");
        String password = scanner.next();
        userService.loginUser(username,password);

    }
    private void showUserMenu(){
        while (userService.isLoogingIn()){
            System.out.println("\n..user Menu...");
            System.out.println("1. Search Train..");
            System.out.println("2. Book Ticket..");
            System.out.println("3. View My Ticket..");
            System.out.println("4. Cancle Ticket..");
            System.out.println("5. view all Ticket..");
            System.out.println("6. LogOut..");
            System.out.println("Enter Choice");
            int choice = scanner.nextInt();
            switch (choice){
                case 1 -> searchTrain();             // searchTrain() method call
                case 2 -> bookTicket();              // bookTicket() method call
                case 3 -> viewMyTickets();           // user-wise ticket display
                case 4 -> cancelTicket();            // ticket cancel using ticket ID
                case 5 -> bookingService.ListAllTrain();          // admin/user can see all tickets
                case 6 -> userService.logOutUser();      // logout method call
                default -> System.out.println("Invalid choice, please try again.");

            }

        }

    }
    private void searchTrain() {
        System.out.println("Enter source station:");
        String source = scanner.next();

        System.out.println("Enter destination station:");
        String destination = scanner.next();

        List<Train> trains = bookingService.searchTrain(source, destination);

        if (trains.isEmpty()) {
            System.out.println("No Trains Found between " + source + " and " + destination);
            return;
        }

        System.out.println("Trains Found:");
        for (Train train : trains) {
            System.out.println(train);
        }
        System.out.println("Do you want to book ticket ? (yes/no):");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter Train ID to book:");
            int trainID = scanner.nextInt();

            System.out.println("Enter number of seats to book:");
            int seats = scanner.nextInt();

            Ticket ticket = bookingService.bookTicket(userService.getCurrentUser(),trainID,seats);
            if (ticket!=null){
                System.out.println("Booking Successful");
                System.out.println(ticket);
            }
        }
        else {
            System.out.println("Returning to user menu... ");
        }

    }

    private void bookTicket(){
        System.out.println("Enter source station:");
        String source = scanner.next();

        System.out.println("Enter destination station:");
        String destination = scanner.next();

        List<Train> trains = bookingService.searchTrain(source, destination);

        if (trains.isEmpty()) {
            System.out.println("No Trains Found between " + source + " and " + destination);
            return;
        }
        System.out.println("Enter Train ID to book:");
        int trainID = scanner.nextInt();

        System.out.println("Enter number of seats to book:");
        int seats = scanner.nextInt();

        Ticket ticket = bookingService.bookTicket(userService.getCurrentUser(),trainID,seats);
        if (ticket!=null){
            System.out.println("Booking Successful");
            System.out.println(ticket);
        }

    }
    private void viewMyTickets() {
        List<Ticket> ticketByUser = bookingService.getTicketByUser(userService.getCurrentUser());
        if (ticketByUser.isEmpty()) {
            System.out.println("No Ticket Booked yet");
        } else {
            System.out.println("Your Tickets:");
            for (Ticket ticket : ticketByUser) {
                System.out.println(ticket);
            }
        }
    }
    private void cancelTicket(){
        System.out.println("Enter Ticket ID to Cancel:");
        int ticketID = scanner.nextInt();
        bookingService.cancelTicket(ticketID,userService.getCurrentUser());
    }




    private void exitApp(){
        System.out.println("Thank You For Using This App");
        System.exit(0);
    }

}
