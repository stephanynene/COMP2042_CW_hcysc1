# COMP2042_CW_hcysc1 
## Space Brick Breaker Game
#### Version 12.12.2023 


# Compilation Instructions:
### Prerequisites
Install Java Development Kit (JDK) (version 8 or later).
Download and install IntelliJ IDEA

Load/clone the repository project onto Intellij IDEA. This is an Intellij IDE created project so ensure 
that when loading to Eclipse the correct steps are carried out for importing.

Open the project folder. Configure Project:
Set the Project SDK to your installed JDK version.
Configure dependencies.
Build Project: Select "Build" -> "Build Project" from the menu.
Run the Game: Find and run the main class with the main method.


# Implemented and Working Properly:

1. Timer Function

I implemented a timer function to set a game time limit of 100 seconds for every level.
The time is shown on the middle top of the screen and counts down from 100. 
This way it's harder as players can only spend a certain time on that level. 
If the 100 seconds are over before the bricks are cleared, the player loses. 
If the bricks are cleared, the player advances to the next level and the timer resets to 100 seconds again. 



2. Thunder Block

I added this thunder block for a visual distraction effect. It generates a thunder sound 
and the screen will display a shaking animation. The player will gain 10 points from hitting 
a thunder block. A label will show indicating 10 points earned. 

3. Bad Bonuses

I refactored the bonus choco block to also drop "bad" bonuses. Instead of just dropping good bonuses,
 and it being collected by the paddle all the time, a bad bonus can drop. The image is a bomb image. 
 If the player's break paddle touches these, they will lose a heart. 

4. Sturdy Block

A sturdy block, with a wood like image, requires 3 hits to be broken. I defined a durability for the blocks. 
Normal blocks affected by this but for the sturdy block the durability will decrease with every hit until the block can be broken. 
It is reconigsed by the wood-crunch sound an look. 

5. Ball Rebound Effect

Instead of the ball htting a block and just continuing straight through it, I refactored the game so 
that the ball will rebound off the block. This makes the game more appealing and harder as the ball will travel away.

6. Save and Load Function

I added the load function to screen when the game starts. The player can choose to start a new game
 or load a saved game. It also works with the saved remaining time. 

7. Mute Function

After adding the background music, I implemented a mute function where the player can press M on the keyboard to mute the background music. The rest of the sound effects still sound except for the background music. 

7. Final Score Print

At the win stage, the screen will show a "YOU WON!" message and a "Your score: " message which displays the final score obtained 

8. Smoother Breakpaddle

I made the breakpaddle smoother instead of a choppy, jolting feeling when moving the breakpaddle

9. Visual Changes 

I changed the look of the background, ball, breakpaddle and label looks. I imported resources to the resources file to do this and also edited the style css file for this. I also changed the look of the game when the gold star block is hit and 
the gold ball is present. 

10. Faster Ball 

I increased the speed of the ball in general and also when the ball is gold, it travels even faster in comparison to the 
normal ball. 

11. Sound Effects

I added looping background music that starts when the player presses start game or load game.
It stops if the player loses or the player wins. It can be muted by pressing M. 

I added sound effects:

Bounce sound - when ball hits normal blocks, the breakpaddle, any walls
Lose sound - when the player loses a heart if collecting a bad bonus, or hitting the bottom boundary. 
Level up sound - for when player advances to next level
Win sound - For when player wins 
Thunder sound - for thunder block collision 
Coin sound - Gold star block hit sound 
Advance sound - Bonus collected sound 
Game over sound - when player loses hearts until heart is 0 or if time is up 
Wood sound - for collisions with the sturdy block

12. Make ball spawn from centre
Made the ball spawn at the centre every timea



# Features Not Implemented: Identify any features that you were unable to
# implement and provide a clear explanation for why they were left out.

1. Scoreboard

I tried to implement the scoreboard but failed in doing so. There was a step that required me to write values into a file
 and save it there whenever the player wins or loses but there was a continuous error that didnt allow me to do so. 
 Therefore I couldn't create the score board. I also had trouble navigating to a separate page to be able to show the 
 scores. 

I tried to move the loadGame method away from main class to a separate class but it required many parameter passes that 
seemed too lengthy for a maintainable software development. 




#  New Java Classes: Enumerate any new Java classes that you introduced for the
# assignment. Include a brief description of each class's purpose and its location in the code.


Alot of the relevant code moved into these classes came from the main class and exisiting classes.

1. GameConstants enum class
Location: In the constants package

Purpose: The enum class is used to define constants throughout the game. 
Everywhere in the code that I saw constants I moved to there. Classes use these variables by calling GameConstants.variable 
and either getting the string or int value. 

2. ConcretePhysicsEngine

Location: controller package
Purpose:
The ConcretePhysicsEngine class serves as a concrete implementation of the PhysicsEngine interface. 
It manages the physics interactions between crucial game elements, specifically the ball and the BreakPaddle. This class 
ensures that the game's physics, such as ball movement, collisions, and reactions to game elements, are handled effectively.


I moved relevant and extremely lengthy method SetPhysicsToBall() from the main class to this class.

Ball Movement: Manages the movement of the ball based on its direction, velocity, and collision conditions

Collision Handling: Detects and handles collisions between the ball and various game elements, such as walls, the BreakPaddle, and blocks.

Velocity Calculation: Calculates and updates the velocity of the ball based on time and collision conditions. 

Boundary Collisions: Ensures that the ball reacts appropriately when hitting the top, bottom, left, or right boundaries of the game scene.

BreakPaddle Interaction: Handles collisions between the ball and the BreakPaddle, adjusting the ball's movement direction and velocity accordingly.

Flag Management: Maintains flags indicating different collision scenarios, ensuring accurate ball behavior.


3. ElementsUpdater
Location: controller package
Purpose:
The ElementsUpdater class is responsible for managing the dynamic updating of various game elements, including  UI, block
 collisions, and bonuses. Its a component within the game engine, ensuring that visual and interactive aspects of the game 
 remain synchronised and responsive. I moved the related methods from the main class to this class. 


UI Updates: Handles the real-time updating of UI elements, such as score labels, heart labels, BreakPaddle, and Ball 
positions.

Block Collisions: Manages collisions between the ball and blocks, updating the game state, and handling specific block hits
 based on collision conditions.

Choco Bonus Handling: Coordinates the creation, update, and removal of Choco Bonuses, including UI representation.

Special Block Effects: Manages the impact of hitting special blocks, such as Star Blocks and Thunder Blocks, on the game state and UI.

Game State Updates: Ensures that the game's state, including scores, destroyed block counts, and gold status, is accurately 
reflected during gameplay.

Thread Synchronization: Safely synchronizes access to shared resources, such as the list of Choco Bonuses, to prevent 
concurrent modification issues - this helped to fix the IndexOutofBounds error too. 


4. LevelManager

Location: controller package
Purpose: Handles level related functions

It contains the nextLevel() and restartGame() method from the main class. 

5. PhysicsEngine (interface)
ConcretePhysicsEngine implements this interface

6. PhysicsUpdater

Location: controller package

Purpose:
The PhysicsUpdater class is responsible for handling physics-related updates during the game loop. It works with the game engine class to ensure that physics calculations, interactions with the BreakPaddle, and updates to bonus items (Chocos) are accurately processed. The relevant methods were taken from the main class. 

Physics Updates: Coordinates physics updates, including setting physics properties for the ball and handling collisions.

Choco Handling: Manages the creation, update, and removal of Choco bonuses, including collision detection with the BreakPaddle.

Gold Status: Monitors and resets the gold status based on elapsed time.

Input Handling: Delegates the movement of the BreakPaddle to the input handler.

Game State Checks: Periodically checks the number of destroyed blocks in the game.

UI Updates: Utilizes JavaFX Platform.runLater to safely update the UI on the JavaFX thread


7. Ball
Location: gameObjects package -> ball package
Purpose:
The Ball class represents the game ball in the Breakout-style game. It encapsulates the properties and behaviors of the 
ball, including its position, velocity, appearance, and interactions with other game elements.


8. BallView
Location: gameObjects package -> ball package
Sets the image fill for ball through method
Sets the size according to radius

9. BlockManager
Location: gameObjects package - > block package

Purpose: visual presentation of blocks collection. 
It holds collections for both the logical representation of blocks (blocks) and their blockViews. 
ensures that blocks are appropriately displayed on the game screen. 

10. BlockView
Location: gameObjects package - > block package
Purpose: Sets the visual appearance of a specific individual block. Such as the size. 
It sets the fill image of the rectangle block according to the block type. 

11. Board
Location: gameObjects package - > board package
Purpose: 
responsible for initializing the game board:
Creates a grid of blocks with various types, colors, and durability, taking into account factors like game level and 
randomization. The types of blocks, including special ones like Chocolate, Heart, Star, Thunder, and Sturdy, are determined
 by random numbers. Color assignment is also randomized, and special blocks, such as Stars Block, spawn less frequently for 
 added challenge. 
The class manages the integration with the main game instance to synchronize and update the list of blocks on the board.

12. BonusView
Location: gameObjects package - > bonus package
Purpose: Sets the image view, size and location of bonus. It set the image of the bonus depending on whether its good or
 bad. If its good it fills a different image to bad.

13. BreakPaddle

Location: gameObjects package - > breakpaddle package
Purpose: Manages movement and animation of a break paddle in a brick-breaking game.
Uses a Timeline animation for smooth left and right movements.
Ensures the break paddle stays within the game scene width.
Animation is handled through the animateMovement method, adjusting the x-coordinate with a specified deltaX value.

14. BreakPaddleView
Location: gameObjects package - > breakpaddle package
Purpose: Sets the visual view of the break paddle like the:
Position,
Size,
Image fill

15. InputHandler
Location: inputHandler package 
Purpose: Handles the logic for input from the user
Includes logic for left, right, save (s), mute (s)

16. BonusLabel
Location: labels package
Purpose: Includes label making methods to animate in a certain way 
17. ScoreLabel
Location: labels package
Purpose:Includes label making methods specific to score

18. GameSaver
Location: saving package 
Purpose: Saves the current state of the game by writing all of the variables to a file 

19. Timer
Location: timer package
Purpose:
Manages time-related operations in the game. It encapsulates the functionality related to tracking the elapsed time, 
countdowns for game time limit and scheduling events at specific intervals. It contains methods to track elapsed time during 
the game. It supports countdown functionality for the game limit.

20. Sounds
Location: sounds package
Purpose: Stores all the sounds neccesary for sound effects. And has methods to play/stop/mute





# Modified Java Classes: List the Java classes you modified from the provided code
# base. Describe the changes you made and explain why these modifications were
# necessary.

For all class's codes, variable calls were changed by using getter methods / setter methods since these were moved to new classes and made private

1. Main

I refactored and simplified the main class by taking out related code and creating new classes to put them in. For example,
 properties/fields/methods about the ball I moved to a new class called Ball. I later then refactored out the visual aspect 
 of the Ball to another class called BallView. I did this for the game objects like Block, Breakpaddle, Board. 

I also took out code related to saving and moved them to new classes like GameSaver. I extracted out the code related to 
handling user input into a new InputHandler class. 

Many of the controller methods used in the main class were also taken out and put into new classes like (Concrete)
PhysicsEngine, ElementsUpdater, LevelManager and PhysicsUpdater. 

I refactored the main class to create the instances of these objects since I took them out of the main class they needed to
 be created in the Main class again. 


2. GameEngine

Refactored the game engine to not use separate threads for updating physicsThread and updateThread. Introduced a timeline 
called updateTimeLine instead. I also removed time thread and used timeline instead. This is to prevent concurrent issues 
that occured during run time. By using timeline animation thread it was easier to read and manage.

This also fixed the run time errors that I experienced during refactoring. 

3. Block
Added an instance of BlockView to use the new BlockView class 
Removed the Block fill setting logic according to the block type to the BlockView class. This way the View and visual of the
 Block is separated from the Model logic 

4. Bonus

I added a isGood boolean variable. This variable is then set randomly in the controller class ElementsUpdater to be true or 
false and used in the PhysicsUpdater class for handling the choco bonus hit to the breakpaddle. 


5. LoadSave

Refactored this class to be named GameStateReader

6. Score
Score class is renamed to be Stats class.
It now holds information / variables about the statistics of the game such as the heart count, time, hit time, gold time. 
I combined it in here as these properties are related to the statistics and would not make sense if I added them to a class
 named Score. 





# Unexpected Problems: Communicate any unexpected challenges or issues you
# encountered during the assignment. Describe how you addressed or attempted to
# resolve them.

1. Run time errors - IndexOutOfBounds Exception in Thread 

I experienced many errors during the refactoring stage of the project to do with IndexOutofBounds Errors. I spent alot of 
time refactoring code related to ArrayLists to do with the Block and Chocos. This didn't help. I introduced the usage of 
TimeLine Animations and Platform.runLater() to make sure ui related updates were made on the JavaFX Thread only. This helped 
fix the error

2. Run time errors - NullError 

I experienced a prominent Game Engine Null error when initialising objects in Main class. I realised this was due to the 
game engine being initialised only after other objects were initalised. And thes objects needed the game engine as 
parameters. Therefore a null game engine was passed as a parameter. But several objects needed to be created with many 
parameters being passed to them and some had to be initalised before others and it created a circular loop. 

I solved this by initialising some things through a constructor of the class and then making a separate constructor for the 
game engine and using that to set the game engine in those classes only after the game engine was intialised. That way the 
game engine wasnt null. 

I also solved it by creating an interface for the PhysicsEngine and many objects needed the PhysicsEngine. Making the 
interface solved this null circular loop too. 

3. Class parameters

The classes I created need many parameters to be passed into them. This is the primary reason of the Null Errors I fixed. I 
never got to refactor the large number of paramters being passed. I considered using a builder pattern but did not due to 
the extra class I would have ot create to do so. 


