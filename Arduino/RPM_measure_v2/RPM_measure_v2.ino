#include <Wire.h>

const int analogInPin0 = A0;
const int analogInPin1 = A1;
const int threshHold = 10;


int sensorValue[2];
bool triggered[2];

long currentTime[2];
long lastTime[2];
float RPM[2];

void setup() {
  sensorValue[0] = 0;
  sensorValue[1] = 0;
  triggered[0] = false;
  triggered[1] = false;
  
  currentTime[0] = 0;
  lastTime[0] = 0;  
  currentTime[1] = 0;
  lastTime[1] = 0;
  
  Wire.begin(8);                // join i2c bus with address #8
  Wire.onRequest(requestEvent); // register event
}

void loop() {
  sensorValue[0] = analogRead(analogInPin0);
  sensorValue[1] = analogRead(analogInPin1);
  checkSensors(0);
  checkSensors(1);
  delay(2);
}

void checkSensors(int i){
  if (sensorValue[i] < threshHold && !triggered[i]){
     triggered[i] = true;
     math_RPM(i);
  } else if (sensorValue[i] > threshHold){
     triggered[i] = false;
  }
}


void math_RPM(int i){
  lastTime[i] = currentTime[i];
  currentTime[i] = millis();
  RPM[i] = ( (1000/(float)(currentTime[i]-lastTime[i]) )*60);  
}

void requestEvent() {
  String RPM0 = String(RPM[0],3);
  RPM0 = RPM0.substring(0,5);
  String RPM1 = String(RPM[1],3);
  RPM1 = RPM1.substring(0,5);
  String toSend = RPM0+"|"+RPM1;
  char buf[12];
  toSend.toCharArray(buf,12);
  Wire.write(buf); 
}
