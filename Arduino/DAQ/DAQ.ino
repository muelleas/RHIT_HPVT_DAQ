#include<Wire.h>

const int raw2G=16384;
const double raw2Degree=131.07;

const int MPU_addr=0x69;  // I2C address of the MPU-6050  // 0X68 is AD0 grounded
const int pin2 = 10;
const int pin3 = 11;
const int pin4 = 12;
const int pin5 = 13;

//MPU6050 accr1;//(0x68, 0b00000001);
//MPU6050 accr2(0x68, 0b00000010);

int device1 = 0b0001;
int device2 = 0b0010;

double *accelPointer;
double accel[3];
int16_t *gyroPointer;
int16_t gyro[3];
double temp;


void setup() {
 accelPointer = accel;
 gyroPointer = gyro;
  
  pinMode(pin2, OUTPUT);
  pinMode(pin3, OUTPUT);
  pinMode(pin4, OUTPUT);
  pinMode(pin5, OUTPUT); 
  
  Wire.begin();

  setUpMPU(device1);
  setUpMPU(device2);

  Wire.endTransmission(true);
  
  Serial.begin(9600);


}

void setUpMPU(int device){
  setToRead(device);
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x6B);  // PWR_MGMT_1 register
  Wire.write(0);     // set to zero (wakes up the MPU-6050)
}

void loop() {
   setToRead(device2);
   getAccel(accelPointer);
   getGyro(gyroPointer);
   temp = getTemp();
   Serial.println(printData());
   setToRead(device1);
   delay(100);
}

String printData(){
  String data = "";
  data = data+(accel[0]/raw2G)+"|"+(accel[1]/raw2G)+"|"+(accel[2]/raw2G);
  data = data+"|"+(gyro[0]/raw2Degree)+"|"+(gyro[1]/raw2Degree)+"|"+(gyro[2]/raw2Degree);
  data = data + "|"+temp;
  return data;
}

void setToRead(int location){
  digitalWrite(pin2, bitRead(location, 0));
  digitalWrite(pin3, bitRead(location, 1));
  digitalWrite(pin4, bitRead(location, 2));
  digitalWrite(pin5, bitRead(location, 3));  
}
