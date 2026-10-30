# Java Battleship (Console Edition)

Customizable implementation of classic Battleship game, written in Java. 

This project was developed to study OOP principles (Inheritance, Polymorphism, Encapsulation).
It is built as a part of Software Design course at HSE University by Daiana Tei in October 2021.

## 🚀 Features

*   **Customizable Grid**: Define the exact vertical and horizontal dimensions of the Ocean or play on a standard 10x10 grid.
*   **Customizable Fleet**: Choose the exact quantity of Carriers (5 cells), Battleships (4 cells), Cruisers (3 cells), Destroyers (2 cells), and Submarines (1 cell).
*   **Interactive Gameplay**: Fire shots using standard coordinate systems (e.g., `5 d`). The game handles input validation.
*   **Smart Targeting:** Automatically marks surrounding water cells as "misses" (`·`) when a ship is fully sunk, preventing redundant shots and mimicking real human gameplay logic.
*   **Object-Oriented Design:** Distinct classes for entities like `Ocean`, `Fleet`, `Cell`, and specific ship types inheriting from a base `Ship` class.

## 🎮 Gameplay Example

```text
An Ocean of size 10x10 with 
1 Battleships 
2 Cruisers 
3 Destroyers 
4 Submarines 
has been created successfully!
----------------------------------------------------------------------
	| a  b  c  d  e  f  g  h  i  j 
----+----------------------------
1	|                              
2	|                              
3	|          ·  ·  ·             
4	|          ·  X  ·             
5	|          ·  X  ·             
6	|          ·  ·  ·     ·       
7	|                      ·  ·  · 
8	|                      ·  X  · 
9	|                      ·  ·  · 
10	|                              
----------------------------------------------------------------------
Shot #8
The vertical coordinate: 8
The horizontal coordinate: i
Hit!
Congrats! You just have sunk a Submarine.
```

## Running the Project

Open the src folder in your preferred Java IDE and run the Program main class.
