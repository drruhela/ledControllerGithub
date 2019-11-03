/* Code Written by Roy Pe'er. I've explained all the code in the grey comments.
I recommend you to go over the code, examine it, play with it, improve it and modify it according to your needs. 
For more awesome tutorials, subsrice to my channel:
http://www.youtube.com/subscription_center?add_user=Roypeer1
*/

import processing.serial.*; //Importing the Serial library.
Serial myPort; // Creating a port variable.
int r,g,b; // initializing colours.
String T= "WATCOPTER CONTROLS"; // Creating word strings for the interface.
String T1= "FWD";
String T2= "RGT";
String T3= "LFT";
String T4= "BWD";
String T5= "BRAKE"; 
String T6 = "UP";
String T7 = "DOWN";

void setup()
{
  size(1000,600); // Creating the display window and defining its' size.
 
  r = 0; // Setting up the colours.
  g = 0;
  b = 0;

println(Serial.list()); // IMPORTANT: prints the availabe serial ports.
String portName = Serial.list()[1]; // 1 on Olivia's computer, 2 on Devika's
  myPort = new Serial(this, portName, 9600); // Initializing the serial port.
}
 
void draw()
{
   
background(229,238,255); //Light Blue Background.

fill (51,125,255); // Painting the Arrows White.
rect(900, 30,70,70,15);//ON/OFF button
rect(750, 250, 100, 100,100); // circle
triangle(750, 235, 800, 160, 850, 235); //FWD triangle
triangle(735, 350, 660, 300, 735, 250); //RHT triangle
triangle(865, 250, 940, 300, 865, 350); //LFT triangle
triangle(750, 365, 800, 440, 850, 365); //BWD triangle
rect(50, 135, 400, 100,5); //UP rectangle
rect(50, 335, 400, 100,5); //DOWN rectangle
textSize(32); // Defining the headline's size- 32
fill (0,0,255); // painting the headline blue.
text(T, 1000/2 - 1000/5, 50);  // placing the headline in a specific location
textSize (20); // The arrow keys text size- 20
fill (153,187,255); // painting it green.
text(T1, 780, 220); //FWD
text(T2, 875, 310);//RHT
text(T3, 690, 310); //LFT
text(T4, 780, 400);//BWD
text(T6, 225, 195); //UP
text(T7, 210, 395); //DOWN
}
 
void keyPressed()
{
 
 
  switch (keyCode) 
  { 
    case UP:
    myPort.write('1');
    println("Clockwise");
    fill(255,0,0);
    rect(50, 250, 400, 100,5);
    break;
    case DOWN:
    myPort.write('2');
    println("Counterclockwise");
    fill(255,0,0);
    rect(50, 135, 400, 100,5);
    break;
    
    default:
    println("Salt my jerkey and call me a muskouir, You Sir are in the default!");
    break;
  }
}
