#include <Wire.h>

const int analogInPin = A0;

int sensorValue;
int threshHold;
bool triggered;
int count;

long currentTime;
long lastTime;
float RPM;

void setup() {
  sensorValue = 0;
  threshHold = 10;
  triggered = false;
  count = 0;
  
  currentTime = 0;
  lastTime = 0;
  
  Wire.begin(8);                // join i2c bus with address #8
  Wire.onRequest(requestEvent); // register event
}

void loop() {
  sensorValue = analogRead(analogInPin);
  if (sensorValue < threshHold && !triggered){
     count++;
     triggered = true;
     math_RPM();
  } else if (sensorValue > threshHold){
     triggered = false;
  }
 
  delay(2);
}


void math_RPM(){
  lastTime = currentTime;
  currentTime = millis();
  RPM = ( (1000/(float)(currentTime-lastTime) )*60);  
}

void requestEvent() {
  String toSend = String(RPM,5)+"000000";
  toSend = toSend.substring(0,6);
  char buf[6];
  toSend.toCharArray(buf,6);
  Wire.write(buf); 
}
