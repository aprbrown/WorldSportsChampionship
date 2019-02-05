import java.util.Objects;

/**
 * The {@code Event} class is used to represent an event at the World Sports Championship. The class holds the name of
 * the event along with the number of tickets.
 *
 * @author Andrew Brown
 */
public class Event implements Comparable<Event> {
    private String name;
    private int ticketsRemaining;

    /**
     * Constructor taking the name of the event and number of tickets for the event
     *
     * @param name String event name
     * @param tickets int number of tickets
     */
    Event(String name, int tickets) {
        this.name = name;
        this.ticketsRemaining = tickets;
    }

    /**
     * Package-private method to get the event name
     * @return String events name
     */
    String getName() {
        return name;
    }

    /**
     * Package-private method to get the number of tickets
     *
     * @return int Number of tickets
     */
    int getTicketsRemaining() {
        return ticketsRemaining;
    }

    /**
     * Package-private method to set the number of tickets for the event
     *
     * @param ticketsRemaining int number of tickets to change to
     */
    void setTicketsRemaining(int ticketsRemaining) {
        this.ticketsRemaining = ticketsRemaining;
    }

    /**
     * Method to get a String representation of the event object
     * @return String concatenation of event name and number of tickets with a dash between them
     */
    @Override
    public String toString() {
        return name + " - " + ticketsRemaining;
    }

    /**
     * Compares two event objects and determines the difference between them
     * @param e Event object to compare with
     * @return int Comparison value
     */
    @Override
    public int compareTo(Event e) {
        int eventNameCompare = name.compareTo(e.name);
        if (eventNameCompare != 0) return eventNameCompare;
        else return ticketsRemaining - e.ticketsRemaining;
    }

    /**
     * Checks if object is equal to another based on event name
     * @param o Object to compare with
     * @return boolean whether objects are the same or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(name, event.name);
    }

    /**
     * Generates a hashcode for the object based on event name
     * @return int hashcode
     */
    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}