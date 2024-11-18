# River Crossing Game

The **River Crossing Game** is a software engineering project that simulates and extends classic river-crossing puzzles, such as the "Farmer, Wolf, Goose, and Beans" game. This project demonstrates principles of clean code, extensibility, and rigorous testing by designing a modular game engine and an interactive GUI.

---

<p align="center">
  <img src="https://github.com/chhsch/River-Crossing-Game/blob/85cb5f6ab54a1c2308d68fd8a2e0629e496d1b21/img/farmer.png" alt="Farmer" width="400" height="200"/>
  <img src="https://github.com/chhsch/River-Crossing-Game/blob/85cb5f6ab54a1c2308d68fd8a2e0629e496d1b21/img/farmer_win.png" alt="Farmer Win" width="400"height="200"/>
</p>
<p align="center">
  <img src="https://github.com/chhsch/River-Crossing-Game/blob/85cb5f6ab54a1c2308d68fd8a2e0629e496d1b21/img/monster.png" alt="Monster" width="400"height="200"/>
  <img src="https://github.com/chhsch/River-Crossing-Game/blob/85cb5f6ab54a1c2308d68fd8a2e0629e496d1b21/img/monster_lost.png" alt="Monster Lost" width="400"height="200"/>
</p>




## Features

### Core Functionality
1. **Farmer Game**:
   - The farmer must transport the wolf, goose, and beans across the river without violating constraints:
     - The wolf cannot be left alone with the goose.
     - The goose cannot be left alone with the beans.
   - Includes an interactive GUI for game play.

![Alt text](https://github.com/chhsch/River-Crossing-Game/blob/85cb5f6ab54a1c2308d68fd8a2e0629e496d1b21/img/farmer.gif)

2. **Monster Game**:
   - An extended river-crossing puzzle with monsters and munchkins.
   - Supports up to 6 items, with additional constraints:
   - Monsters cannot outnumber munchkins on either shore.
     
![Alt text](https://github.com/chhsch/River-Crossing-Game/blob/85cb5f6ab54a1c2308d68fd8a2e0629e496d1b21/img/monster.gif)


3. **Scout Game**:
   - Implement a new ScoutGameEngine class that extends the existing AbstractGameEngine.
   - Modify the RiverGUI to include a scout restart button, ensuring minimal impact on the existing code structure and functionality.

![Alt text](https://github.com/chhsch/River-Crossing-Game/blob/85cb5f6ab54a1c2308d68fd8a2e0629e496d1b21/img/scout.gif)


### Design Highlights
- **AbstractGameEngine**:
  - Provides a flexible base class for multiple game engines.
  - Implements default behaviors for most methods in the `GameEngine` interface.
  
- **GameEngine Interface**:
  - Defines a contract for all game engines.
  - Ensures modularity and compatibility with different game variations.

- **Extensibility**:
  - Easily extendable to new river-crossing puzzles with minimal changes.

- **Testing**:
  - Comprehensive test cases using JUnit ensure correctness and reliability.

---

## How to Play

1. **Run the Game**:
   - Launch the `RiverGUI` class to start the game.
   - Use the graphical interface to interact with the game.

2. **Game Rules**:
   - Click on an item to load or unload it from the boat.
   - Click on the boat to row it across the river.
   - Follow the game-specific rules to win:
     - Safely transport all items to the other side of the river.



### Key Concepts
- Object-Oriented Design:
  - Use of enums, encapsulation, and abstraction.
  - Modular design with clearly defined interfaces.
- Refactoring:
  - Improved code readability and maintainability.
- Testing:
  - Rigorous test cases ensure the correctness of game logic.

### Core Components
1. **GameEngine Interface**:
   - Defines methods for managing items, boat movement, and game state.

2. **FarmerGameEngine**:
   - Implements the logic for the classic farmer game.

3. **MonsterGameEngine**:
   - A variation of the game with monsters and munchkins.

4. **RiverGUI**:
   - Provides an interactive graphical interface for the game.

5. **GameEngineTest**:
   - Ensures the correctness of the game engine through unit tests.
