import java.io.*;
import java.util.*;

/**
 * Main driver class containing main method for the software. Instantiates SortedArrayLists to store Clients and Events
 * used in the application, a PrintWriter for file output and scanner for input from file and user input. Additionally
 * a NEWLINE constant has been created to ensure cross platform compatibility for line separator characters.
 *
 * @author Andrew Brown
 */
public class WorldSportsChampionship {
    private static final String NEWLINE = System.getProperty("line.separator");
    private static Scanner input = new Scanner(System.in);
    private static SortedArrayList<Event> events = new SortedArrayList<>();
    private static SortedArrayList<Client> clients = new SortedArrayList<>();

    public static void main(String[] args) throws IOException{
        setup();
        printMenu();
    }

    /**
     * Setup method which takes a file named input from the folder named InputOutput. The file will be written with a
     * standard format so any file using this format should work. The method firstly checks the first line for the number
     * of events in the file. This number is used as the basis for a for loop which checks n line pairs for event name
     * and number of tickets and adds each to the events SortedArrayList.
     *
     * The next line checks for the number of clients and similarly uses this for a for loop and checks the next n lines
     * for the client names and adds them to the clients SortedArrayList.
     */
    private static void setup() throws IOException {
        try {
            BufferedReader inFile = new BufferedReader(new FileReader("input.txt"));
            int numberOfEvents = Integer.parseInt(inFile.readLine());
            for (int i = 0; i < numberOfEvents; i++) {
                String eventName = inFile.readLine();
                int numberOfTickets = Integer.parseInt(inFile.readLine());
                events.add(new Event(eventName, numberOfTickets));
            }
            int numberOfClients = Integer.parseInt(inFile.readLine());
            for (int j = 0; j < numberOfClients; j++) {
                String clientName = inFile.readLine();
                String[] names = clientName.split(" ");
                String firstName = names[0];
                String lastName = names[1];
                clients.add(new Client(firstName, lastName));
            }
        } catch (FileNotFoundException e) {
            System.out.println(NEWLINE + "input.txt file is missing from InputOutput folder");
            System.exit(1);
        }
    }

    /**
     * Method to print the main menu providing the user with options to control the application. A switch statement is
     * used to take user option and run a relevant method when each option is called.
     */
    private static void printMenu() throws IOException {
        System.out.println("---------------------------------------");
        System.out.println("| [e] Display Event Info              |");
        System.out.println("| [c] Display Client Info             |");
        System.out.println("| [b] Sell Tickets                    |");
        System.out.println("| [r] Cancel/Return Tickets           |");
        System.out.println("|                                     |");
        System.out.println("| [f] Exit Program                    |");
        System.out.println("---------------------------------------");
        System.out.print("Please make a selection and press Enter: ");

        try {
            char menuOption;
            menuOption = input.nextLine().toLowerCase().charAt(0);
            switch (menuOption) {
                case 'e':
                    displayEventInfo();
                    break;

                case 'c':
                    displayClientInfo();
                    break;

                case 'b':
                    sellTicket();
                    break;

                case 'r':
                    cancelTicket();
                    break;

                case 'f':
                    exitProgram();
                    break;

                default:
                    printMenu();

            }
        } catch (StringIndexOutOfBoundsException e) {
            printMenu();
        }
    }

    /**
     * Method to show all the event information, displaying the event name and the number of tickets that remain for each
     * event. The data is shown in a formatted manner to display in columns to increase readability.
     */
    private static void displayEventInfo() throws IOException {
        System.out.println(NEWLINE + "                 ALL EVENT INFORMATION                ");
        System.out.println("------------------------------------------------------");
        for (Event e : events) {
            System.out.printf("%-30.30s  %-30.30s%n",
                    "Event: " + e.getName(), "Available Tickets: " + e.getTicketsRemaining());
        }
        System.out.println("------------------------------------------------------" + NEWLINE);
        printMenu();
    }

    /**
     * Method to show all client info. The name of the client is shown and then the events they are attending are shown
     * on the following line indented from the left edge by a tab space.
     */
    private static void displayClientInfo() throws IOException {
        System.out.println(NEWLINE + "                 ALL CLIENT INFORMATION");
        System.out.println("------------------------------------------------------");
        for (Client c : clients) {
            System.out.println("Client Name: " + c.getFullName());
            if (c.getEventsAttending().isEmpty()) {
                System.out.println("\tEvents Attending: No Tickets Bought Yet");
            } else {
                System.out.println("\tEvents Attending: " + c.getEventsAttending());
            }
        }
        System.out.println("------------------------------------------------------" + NEWLINE);
        printMenu();
    }

    /**
     * Asks the user which client they would like to sell a ticket to. The name is checked against the list of clients
     * and if the client isn't found the user is presented an option to try again. If the client has already purchased
     * tickets for three events then the user is informed that the limit for that client has been reached. If the client
     * is found and they don't already have tickets for 3 events, the client object is then passed into the
     * sellTicketToClient method.
     */
    private static void sellTicket() throws IOException {
        System.out.print(NEWLINE + "Which client would you like to sell tickets to? ");
        String response = input.nextLine();
        Client client = null;
        for (Client c : clients) {
            if (c.getFullName().equals(response)) {
                client = c;
            }
        }
        if (client == null) {
            System.out.println("Cannot find that client");
            System.out.print("Try again? (y)es/(n)o ");
            char choiceResponse = input.nextLine().toLowerCase().charAt(0);
            if (choiceResponse == 'y') {
                sellTicket();
            } else {
                printMenu();
            }
        }

        if (client != null && client.getEventsAttending().size() == 3) {
            System.out.println("Sorry a client is only able to buy tickets for up to 3 events");
            printMenu();
        }
        if (client != null) {
            sellTicketToClient(client);
        }
    }

    /**
     * Asks the user which event the client would would like to purchase a ticket for. The name is checked against the
     * list of events on record. If the event isn't found the user is informed and asked if they want to try again. If
     * the event is found but the number of tickets remaining is 0, the method noTicketsLetter is called with the client
     * and event info passed into it, the user is informed and asked if they want to check a different event. If there
     * are tickets available, then the client and event are passed into the sellTicketForEvent method.
     *
     * @param client Client to sell tickets to
     */
    private static void sellTicketToClient(Client client) throws IOException {
        System.out.print("Which event would " + client.getFullName() + " like to buy tickets for? ");
        String response = input.nextLine();
        Event event = null;
        for (Event e : events) {
            if (e.getName().equals(response)) {
                event = e;
            }
        }
        if (event == null) {
            System.out.println("Cannot find that event");
            System.out.print("Try again? (y)es/(n)o ");
            char choiceResponse = input.nextLine().toLowerCase().charAt(0);
            if (choiceResponse == 'y') {
                sellTicketToClient(client);
            } else {
                printMenu();
            }
        }

        if (event != null && event.getTicketsRemaining() == 0) {
            noTicketsLetter(client, event);
            System.out.println("No more tickets available for " + event.getName());
            System.out.print("Would you like to choose a different event? (y)es/(n)/o ");
            char choiceResponse = input.nextLine().toLowerCase().charAt(0);
            if (choiceResponse == 'y') {
                sellTicketToClient(client);
            } else {
                printMenu();
            }
        }

        if (event != null) {
            sellTicketForEvent(client, event);
        }
    }

    /**
     * The user is asked how many tickets the client would like to purchase. If there are fewer tickets available than
     * have been requested the user is informed how many tickets remain and asked if they would like to enter a different
     * value. If the number of tickets is valid, then the client, event and number of tickets is passed into the
     * confirmTicketSale method. If the user enters a non-valid entry for the number of tickets, an exception is thrown
     * and the method is called again. If a number lower than 0 is entered it is replaced with 0.
     *
     * @param client Client to sell ticket to
     * @param event Event which the tickets are for
     */
    private static void sellTicketForEvent(Client client, Event event) throws IOException {
        System.out.print("How many tickets would " + client.getFullName() + " like to buy for " +
                event.getName() + "? ");
        try {
            int response = input.nextInt();
            input.nextLine();

            if (response < 0) {
                response = 0;
            }

            if (event.getTicketsRemaining() < response) {
                System.out.println(NEWLINE + "There are only " + event.getTicketsRemaining() + " tickets remaining");
                System.out.print("Would you like to enter a new value? (y)es/(n)o ");
                char choiceResponse = input.nextLine().toLowerCase().charAt(0);

                if (choiceResponse == 'y') {
                    sellTicketForEvent(client, event);
                } else {
                    printMenu();
                }
            } else {
                confirmTicketSale(client, event, response);
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Positive whole number expected");
            input.nextLine();
            sellTicketForEvent(client, event);
        }
    }

    /**
     * The user is asked if the information is correct and if the user responds with yes, then the event and number of
     * tickets is added to the record of the client. Additionally the number of tickets for the event in the events list
     * is decremented by the number of tickets sold. If the customer already owns tickets for the event, the event is
     * updated with the new total.
     *
     * @param client Client to sell tickets to
     * @param event Event tickets are for
     * @param numberOfTickets Int number of tickets to sell
     */
    private static void confirmTicketSale(Client client, Event event, int numberOfTickets) throws IOException {
        System.out.println(NEWLINE + "You are about to sell " + numberOfTickets + " ticket(s) to "
                + client.getFullName() + " for " + event.getName());
        System.out.print("Is this information correct? (y)es/(n)o ");
        char response = input.nextLine().toLowerCase().charAt(0);
        if (response == 'y') {

            if (!client.getEventsAttending().contains(event)) {
                client.addEventToClient(event.getName(), numberOfTickets);
                event.setTicketsRemaining(event.getTicketsRemaining() - numberOfTickets);
                printMenu();
            } else {
                Event clientEvent = null;
                for (Event e: client.getEventsAttending()) {
                    if (e.equals(event)) {
                        clientEvent = e;
                    }
                }
                clientEvent.setTicketsRemaining(clientEvent.getTicketsRemaining() + numberOfTickets);
                event.setTicketsRemaining(event.getTicketsRemaining() - numberOfTickets);
                printMenu();
            }




        } else {
            printMenu();
        }
    }

    /**
     * Asks the user which client would like to return tickets. The name is checked against the list of clients and if
     * the client isn't found the user is asked if they would like to try again. If the client doesn't have any tickets
     * for any event, the user is informed that there are no tickets to be found. If the client is found and has tickets
     * to return, the user is shown which events the client is attending and how many tickets they have for each, the
     * client object is then passed into the cancelTicketForClient method.
     */
    private static void cancelTicket() throws IOException {
        System.out.print(NEWLINE + "Which client would like to return tickets? ");
        String response = input.nextLine();
        Client client = null;
        for (Client c : clients) {
            if (c.getFullName().equals(response)) {
                client = c;
            }
        }
        if (client == null) {
            System.out.println("Cannot find that client");
            System.out.print("Try again? (y)es/(n)o ");
            char choiceResponse = input.nextLine().toLowerCase().charAt(0);
            if (choiceResponse == 'y') {
                cancelTicket();
            } else {
                printMenu();
            }
        }

        if (client != null && client.getEventsAttending().isEmpty()) {
            System.out.println("That client doesn't have any tickets to return.");
            System.out.print("Choose a different client? (y)es/(n)o ");
            char choiceResponse = input.nextLine().toLowerCase().charAt(0);
            if (choiceResponse == 'y') {
                cancelTicket();
            } else {
                printMenu();
            }
        }

        if (client != null) {
            System.out.println(NEWLINE + "The events that " + client.getFullName() + " has tickets for are:");
            for (Event e : client.getEventsAttending()) {
                System.out.printf("%-20s %-20s", e.getName(), "Tickets: " + e.getTicketsRemaining());
                System.out.println("");
            }
            cancelTicketForClient(client);
        }
    }

    /**
     * The user is asked which event the passed in client would like to return tickets for. The client's event list is
     * then checked to verify they have tickets for that event. If the event isn't found the user is informed and asked
     * if they would like to check for a different event. If the event is found then the client and found event are passed
     * into the cancelTicketForEvent method.
     *
     * @param client Client to return tickets
     */
    private static void cancelTicketForClient(Client client) throws IOException {
        System.out.print(NEWLINE + "Which event would " + client.getFullName() + " like to return tickets for? ");
        String response = input.nextLine();
        Event event = null;
        for (Event e : client.getEventsAttending()) {
            if (e.getName().equals(response)) {
                event = e;
            }
        }

        if (event == null) {
            System.out.println("Cannot find that event");
            System.out.print("Try again? (y)es/(n)o ");
            char choiceResponse = input.nextLine().toLowerCase().charAt(0);
            if (choiceResponse == 'y') {
                cancelTicketForClient(client);
            } else {
                printMenu();
            }
        }

        if (event != null) {
            cancelTicketForEvent(client, event);
        }
    }

    /**
     * The user is informed how many tickets the client has for the selected event and then asked how many of the tickets
     * the client would like to return. The number is checked against the number of tickets and if they are trying to
     * return more tickets than they own the user is informed and asked if they would like to enter a new value. If the
     * value is valid i.e. less than or equal to the number of owned tickets, then the client, event and number of tickets
     * is passed into the confirmTicketCancel method. If a number lower than 0 is entered it is replaced with 0.
     *
     * @param client Client to return tickets
     * @param event Event to return tickets for
     */
    private static void cancelTicketForEvent(Client client, Event event) throws IOException {
        System.out.println(NEWLINE + client.getFullName() + " has " + event.getTicketsRemaining() + " tickets for "
                + event.getName());
        System.out.print("How many tickets would " + client.getFullName() + " like to return? ");
        try {
            int numberOfTickets = input.nextInt();
            input.nextLine();

            if (numberOfTickets < 0) {
                numberOfTickets = 0;
            }

            if (numberOfTickets > event.getTicketsRemaining()) {
                System.out.println(client.getFullName() + " doesn't have that many tickets to return");
                System.out.print("Would you like to enter a new value (y)es/(n)o ");
                char choiceResponse = input.nextLine().toLowerCase().charAt(0);

                if (choiceResponse == 'y') {
                    cancelTicketForEvent(client, event);
                } else {
                    printMenu();
                }
            } else if (numberOfTickets <= event.getTicketsRemaining()) {
                confirmTicketCancel(client, event, numberOfTickets);
            }
        } catch (InputMismatchException e) {
            System.out.println(NEWLINE + "Error: Whole number expected" + NEWLINE);
            input.nextLine();
            cancelTicketForEvent(client, event);
        }
    }

    /**
     * The user is shown the client, event and number of tickets they are about to return and asked if the information
     * is correct. If the user responds with yes then the number of tickets to return is checked against the number of
     * tickets available to return. If the numbers are equal, the number of tickets for the event in the main events
     * list is increased by the number returned tickets and the event is removed from the client's event list. If the
     * number of tickets to return is less than the number of available tickets then the tickets in the customer event
     * is reduced and the event in the main events list is increased.
     *
     * @param client Client to return tickets
     * @param event Event tickets are to be returned for
     * @param numberOfTickets int Number of tickets to return
     */
    private static void confirmTicketCancel(Client client, Event event, int numberOfTickets) throws IOException {
        System.out.println(NEWLINE + "You are about to return " + numberOfTickets + " ticket(s) from "
                + client.getFullName() + " for " + event.getName());
        System.out.print("Is this information correct? (y)es/(n)o ");
        char response = input.nextLine().toLowerCase().charAt(0);
        if (response == 'y') {
            if (numberOfTickets == event.getTicketsRemaining()) {
                for (Event e : events) {
                    if (e.equals(event)) {
                        e.setTicketsRemaining(e.getTicketsRemaining() + numberOfTickets);
                        client.removeEventFromClient(e);
                    }
                }
            } else if (numberOfTickets < event.getTicketsRemaining()) {
                for (Event e1 : events) {
                    if (e1.equals(event)) {
                        e1.setTicketsRemaining(e1.getTicketsRemaining() + numberOfTickets);
                        for (Event e2 : client.getEventsAttending()) {
                            if (e2.equals(event)) {
                                e2.setTicketsRemaining(e2.getTicketsRemaining() - numberOfTickets);
                            }
                        }
                    }
                }
            }
        } else {
            printMenu();
        }
        printMenu();
    }

    /**
     * Method to print a letter to file informing the client that there are no tickets remaining for the selected event.
     * The file is generated as a .txt file with the naming convention of ClientLastNameClientFirstNameEventName.txt
     * and is stored in a Letters folder within the InputOutput folder.
     *
     * @param client Client letter is addressed to
     * @param event Event where no tickets are available
     */
    private static void noTicketsLetter(Client client, Event event) throws IOException {
        PrintWriter output = new PrintWriter(new FileWriter("output.txt", true));

        output.println(NEWLINE + "------------------------------------------------------");
        output.println("Dear " + client.getFullName());
        output.println(NEWLINE + "Unfortunately, no tickets remain for " + event.getName());
        output.println(NEWLINE + "Regards,");
        output.println("The Management");
        output.println("------------------------------------------------------");

        output.close();
    }



    /**
     * Asks user if they are certain they would like to quit the application and if they answer yes the application
     * exits.
     */
    private static void exitProgram() throws IOException {
        System.out.print(NEWLINE + "Are you sure you would like to quit? (y)es/(n)o ");
        char selection = input.nextLine().toLowerCase().charAt(0);
        if (selection == 'y') {
            System.out.println("Goodbye!");
            System.exit(0);
        } else {
            printMenu();
        }
    }

}