/**
 * The {@code Client} class is used to represent a clients who attend a World Sports Championship. The class holds
 * the First and Last name of the client along with a list of events they are attending.
 *
 * @author Andrew Brown
 */
public class Client implements Comparable<Client> {
    private String firstName;
    private String lastName;
    private SortedArrayList<Event> eventsAttending;

    /**
     * Constructor taking two strings for first and last name. Instantiated object has an empty SortedArrayList
     * to hold the events the client will attend.
     *
     * @param firstName String representing client's first name
     * @param lastName String representing client's last name
     */
    Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        eventsAttending = new SortedArrayList<>();
    }

    /**
     * Package-private method to get the client's first name
     * @return String Client's first name
     */
    String getFirstName() {
        return firstName;
    }

    /**
     * Package-private method to get the client's last name
     * @return String Client's last Name
     */
    String getLastName() {
        return lastName;
    }

    /**
     * Package-private method to get the client's full name
     * @return String Concatenation of firstName and lastName with a space between
     */
    String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Package-private method to get a list of events the client is attending
     * @return SortedArrayList List of events client is attending
     */
    SortedArrayList<Event> getEventsAttending() {
        return eventsAttending;
    }

    /**
     * Package-private method to add an event to the client's list of events by instantiating a new Event object.
     * @param eventName String name of event
     * @param numberOfTickets Integer number of tickets client purhcases
     */
    void addEventToClient(String eventName, int numberOfTickets) {
        eventsAttending.add(new Event(eventName, numberOfTickets));
    }

    /**
     * Package-private method to remove an event from the client's list of events.
     * @param event Event event to remove, checks client's list to make sure the event is present before removal
     */
    void removeEventFromClient(Event event) {
        Event eventToRemove = null;
        for (Event e : eventsAttending) {
            if (e.equals(event)) {
                eventToRemove = e;
            }
        }
        eventsAttending.remove(eventToRemove);
    }

    /**
     * Compares client objects by name only. This software will have no clients with the exact same name as each
     * other.
     * @param c Client to compare with
     * @return int comparison value
     */
    public int compareTo(Client c) {
        int lastNameCompare = lastName.compareTo(c.lastName);
        if (lastNameCompare != 0) return lastNameCompare;
        int firstNameCompare = firstName.compareTo(c.firstName);
        if (firstNameCompare != 0) return firstNameCompare;
        return lastNameCompare + firstNameCompare;
    }

    /**
     * Prints the contents of the client object to string, in the format of name and list of events
     * @return String concatenation of client's full name and list of events attending
     */
    @Override
    public String toString() {
        if (eventsAttending.size() == 0) return getFullName();
        else
            return getFullName() + " " + eventsAttending;
    }

    /**
     * Compares two objects and checks if they are the same or not, check by client name
     * @param o Object to compare
     * @return boolean result of comparison
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (firstName != null ? !firstName.equals(client.firstName) : client.firstName != null) return false;
        return lastName != null ? lastName.equals(client.lastName) : client.lastName == null;
    }

    /**
     * Generates a hashcode for a client object based on client name
     * @return
     */
    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

}