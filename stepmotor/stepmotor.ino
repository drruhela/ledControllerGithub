/*
 * This is the working code for the Stepper motors on pins 2 through 5
 * works with all 4 motors connected in parallel with the bread board
 */

#include <Stepper.h>

//
const int stepsPerRevolution = 200;  // change this to fit the number of steps per revolution
// for your motor

// initialize the stepper library on pins 8 through 11:
char command = '0';
Stepper myStepper(stepsPerRevolution, 2, 3, 4, 5);

void setup() 
{
  myStepper.setSpeed(80);
  // initialize the serial port:
  Serial.begin(9600);
}

void loop() {
  if(Serial.available())
  {
      command = Serial.read();
      if(command == '1')
      {
        //set the speed at 80 rpm
        //myStepper.setSpeed(80);
        //step 1 rev in clockwise direction
        Serial.println("clockwise");
        myStepper.step(stepsPerRevolution);
        //delay(500);
      }
      else if(command == '2')
      {
        //set the speed at 80 rpm
         //myStepper.setSpeed(80);
        //step 1 rev in counterclockwise direction
        Serial.println("counterclockwise");
        myStepper.step(-stepsPerRevolution);
        //delay(500);
      }else if (command == '0') {
        //myStepper.setSpeed(0);
      }
  }
  /*else
  {
    myStepper.setSpeed(0);
  }*/
}
