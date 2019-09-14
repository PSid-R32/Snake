# projects
A compilation of completed, work in progress, and attempted projects

# snake_game
For this project, I attempted to tackle a complicated task
by trying to translate my idea for the game without considering
the complexity, logistics, and technicalities. After pivoting my
idea numerous times, I have come up with the following game.

I made a canvas for the game board and the snake was an array. By
having the snake be an array, I was able to easily create functions
that would check for a collision with itself, or the food. Set interval
animation is used for the snake's movement throughout the game.

I had to implement buttons, a dynamic progress bar, and a status text div. 
Through troubleshooting I was still not able to find a way to implement the 
progress bar in a manner that would actively display the bar width changing as 
the score did.

Additionally, I tried to implement a "party" function. I believe that the reason I 
failed to implement the function as an onClick event may be due to a priority hierarchy that
webkit-animations has. Regardless I have provided it in the headDiv (id=topRow).

Also, in the image folder there is a png of a cartoon I tried to implement but
had trouble applying it to the food function. I believe the issue I may have
ran into was the with canvas itself.

Overall, this was a great challange that helped me understand how to scale projects,
and to not be overly ambitious.
