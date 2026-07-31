# Tic Tac Toe - LLD

## Problem Statement
Design a two-player Tic Tac Toe game that manages the board state, validates
moves, detects a win/draw condition, and supports an n x n grid.

## Requirements
- Two players take turns placing their piece (X / O) on a board
- Validate that a move is legal (cell empty, within bounds, correct player's turn)
- Detect win condition (row, column, diagonal) after each move
- Detect draw condition (board full, no winner)
- Support configurable board size (not hardcoded to 3x3)

## Key Design Decisions
- **Enum for piece type**: `PlayingPiecetype` represents X / O, avoids magic
  strings/chars scattered across the code.
- **Enum for game status**: `GameStatus` (e.g. IN_PROGRESS, WIN, DRAW) keeps
  win/draw detection explicit and avoids relying on boolean flags.
- **Board as its own class**: `Board` owns the grid and exposes safe methods
  to place a piece and check cell state — the game logic doesn't touch the
  raw grid array directly.
- **Game orchestration separated from board state**: `TicTacToe` /
  `PlayingGame` manages turns and win-checking, while `Board` only manages
  grid state — keeps single responsibility clean.

## Classes
| Class | Responsibility |
|---|---|
| `TicTacToe` | Entry point — sets up players and starts the game |
| `PlayingGame` | Manages turn order, move validation, win/draw detection |
| `Board` | Owns the grid, places pieces, checks cell occupancy |
| `PlayingPiecetype` | Enum — X / O |
| `GameStatus` | Enum — game state (in progress / win / draw) |

## How to Run
```bash
mvn compile
mvn exec:java -Dexec.mainClass="tictactoe.TicTacToe"
```

## Tests
```bash
mvn test
```
Located at `src/test/java/tictactoe/TicTacToeTest.java`

## What I'd Improve
- Support for larger boards (n x n) if not already generalized
- AI/bot opponent (e.g. minimax for unbeatable mode)
- Undo move functionality
- Better separation if win-check logic is currently inside `PlayingGame` rather than its own class