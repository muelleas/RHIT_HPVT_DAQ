#include<Wire.h>

const int raw2G=16384;
const double raw2Degree=131.07;
const int devices = 3;

const int MPU_addr=0x69;  // I2C address of the MPU-6050  // 0X68 is AD0 grounded
const int pin2 = 10;
const int pin3 = 11;
const int pin4 = 12;
const int pin5 = 13;
const int device1 = 0b0001;
const int device2 = 0b0010;
const int device3 = 0b0100;

double *accelPointer1;
double *accelPointer2;
double *accelPointer3;
double accel1[3];
double accel2[3];
double accel3[3];
double *gyroPointer;
double gyro[3];
double temp[devices];

String wheelSpeed = "|";

void setup() {
 accelPointer1 = accel1;
 accelPointer2 = accel2;
 accelPointer3 = accel3;
 
 gyroPointer = gyro;
  
  pinMode(pin2, OUTPUT);
  pinMode(pin3, OUTPUT);
  pinMode(pin4, OUTPUT);
  pinMode(pin5, OUTPUT); 
  
  Wire.begin();
  setUpMPU(device1);
  Wire.endTransmission(true);
  delay(10);

  Wire.begin();
  setUpMPU(device2);
  Wire.endTransmission(true);
  delay(10);
  
  Wire.begin();
  setUpMPU(device3);
  Wire.endTransmission(true);
  delay(10);


  Serial.begin(9600);


}

void setUpMPU(int location){
  setToRead(location);
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x6B);  // PWR_MGMT_1 register
  Wire.write(0);     // set to zero (wakes up the MPU-6050)
}

void loop() {
   setToRead(device1);
   getAccel(accelPointer1);
   temp[0] = getTemp();
   
   setToRead(device2);
   getAccel(accelPointer2);
   temp[1] = getTemp();
  
   setToRead(device3);
   getAccel(accelPointer3);
   temp[2] = getTemp();
   
   wheelSpeed = getSpeed();
   
   Serial.println(printData());
   delay(100);
}

String printData(){
  String data = "";
  data = data + (accel1[2]/raw2G) + "|" + (accel2[2]/raw2G) + "|" + (accel3[2]/raw2G);
  data = data + "|" + (getAverageTemp());
  data = data + "|" + wheelSpeed;  // always last
  return data;
}

void setToRead(int location){
  digitalWrite(pin2, bitRead(location, 0));
  digitalWrite(pin3, bitRead(location, 1));
  digitalWrite(pin4, bitRead(location, 2));
  digitalWrite(pin5, bitRead(location, 3));  
}

double getAverageTemp(){
  double total = 0;
  for (int i = 0; i<devices; i++){
    total = total + temp[i];
  }
  return total/devices;
}
