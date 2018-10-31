# Erstiduell
## Build and Run
This is a Netbeans project. Use either Netbeans to compile it or Ant.
If you want to use Ant just type:

$ ant

to compile and:

$ ant run

to run. Once compiled there is also a JAR file in the dist folder that u can use
everywhere:

$ java -jar erstiduell.jar


Please read LICENSE file.

##HowTo use:

* Start software
* in configuration window
  * Choose question library
  * Choose buzzer buttons for each team
  * Choose display for game window
* in controller window
  * You can change the team name by double clicking the team name in the controller window

##Game rules / mechanisms / colors:

* There is a Mario-Cart like penalty for buzzering before countdown is finished (teamname turns green if active)
* Each team can guess wrong as many times as there are answers to the current question (stars above and below teamname show the tries the team has left - teamname turns orange if one try is left and red if none is left)
* The teamname turns blue if the team won the buzzer race and is therefore active

##BACKLOG (no order)
* Enable controlling game via keyboard
* Display name of display (if possible) to make choosing easier
* Add sounds
