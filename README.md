<h1 align="center"> solver README </h1> 


<h4 align="center"> A simple puzzle solver coded in Java used to find the shortest path to a given solution, 
implementing BFS in tandem with backtracking. </h4>

Further documentation is detailed in the code for both the `solver` algorithm and `demo` configurations.

## Usage guidelines
- The code, namely the core `solver` algorithm, belonging to this repo are entirely free to use!
- Crediting the source, however, is preferred.

## What are configurations?
- This algorithm can't just be copied and then instantly deployed; it requires the implementation of a
  configuration that represents the problem you wish to solve.
- A **configuration** is an attempted solution at any stage of a given problem.
  - It is considered *invalid* if it breaks any of the constraints/rules imposed by the problem, thus resulting in an 
  impossible solution. 
  - Conversely, a configuration is the *goal* if it represents a valid solution to the problem.
- A ***successor* configuration** is the state that immediately follows the current step towards solving a problem.
  - This successor is a new configuration that includes *all the choices* made by its parent, plus one additional 
choice, usually included as an array of other possible solutions.

## Example cases
- Provided a configuration, the `solver` algorithm recurses on each possible move made from 
that configuration, which represents some step in solving the puzzle. 
- You can base this configuration (whose required methods are also included in the `Configuration` interface) 
on the two puzzles used as examples here: `src/demo/watch` and `src/demo/crossing`.
- Each puzzle takes in arguments from the command line and displays the shortest path to the solution in standard
output. 


***

This algorithm, as well as the output files for each puzzle, were programmed under the instruction and guidance of 
RIT's CS department as part of assigned coursework. 