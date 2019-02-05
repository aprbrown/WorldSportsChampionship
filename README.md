# WorldSportsChampionship

Final piece of coursework for CSC8001 - Programming and Data Structures at Newcastle University. The purpose of this project was to parse data from a text file which met a particular convention, to determine the starting state of a ticketing service. The program would allow the operator to 'sell' tickets for an event and then a receipt be created for the transaction.

Additionally a "Sorted ArrayList" class was required for this assignment to extend functionality of ArrayList without using existing sort methods

## What I Learned

* Parse data from an input file
* Extend existing classes
* Override methods | create implementation of compareTo, equals and hashCode methods

### From original README

TITLE:
    WorldSportsChampionship - CSC8001 project coursework part 2

AUTHOR:
    Andrew Brown

HOW TO START THE PROGRAM:
    Run WorldSportsChampionship to launch the software
	
SETUP:
    An input file is required to use this software. The file should be placed in the 
    InputOutput folder and should be named input.txt
    The file must follow the following conventions for the software to work:
    Line 1: Number of events to be entered into the championship
    Line 2: Event Name
    Line 3: Number of tickets for the event
    Repeat Line 2 and 3 for the number of events stated in Line 1:
    Line N: Number of clients to be added into the software
    Line N+1: Client first name and last name separated by a space
    Repeat Line N+1 for the number of clients stated in Line N
    
User Instructions
    When shown the main menu, press the letter corresponding to the option you would 
    like to use and press the return key.