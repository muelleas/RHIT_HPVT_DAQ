void getAccel(double *array){
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x3B);
  Wire.endTransmission(false);
  Wire.requestFrom(MPU_addr,6,true);
  *(array+0)=Wire.read()<<8|Wire.read();  // 0x3B (ACCEL_XOUT_H) & 0x3C (ACCEL_XOUT_L)     
  *(array+1)=Wire.read()<<8|Wire.read();  // 0x3D (ACCEL_YOUT_H) & 0x3E (ACCEL_YOUT_L)
  *(array+2)=Wire.read()<<8|Wire.read();  // 0x3F (ACCEL_ZOUT_H) & 0x40 (ACCEL_ZOUT_L)
}

void getGyro(int16_t *array){
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x43);
  Wire.endTransmission(false);
  Wire.requestFrom(MPU_addr,6,true);
  *(array+0)=Wire.read()<<8|Wire.read();  // 0x43 (GYRO_XOUT_H) & 0x44 (GYRO_XOUT_L)
  *(array+1)=Wire.read()<<8|Wire.read();  // 0x45 (GYRO_YOUT_H) & 0x46 (GYRO_YOUT_L)
  *(array+2)=Wire.read()<<8|Wire.read();  // 0x47 (GYRO_ZOUT_H) & 0x48 (GYRO_ZOUT_L)
}

double getTemp(){
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x41);
  Wire.endTransmission(false);
  Wire.requestFrom(MPU_addr,2,true);
  int16_t Tmp=Wire.read()<<8|Wire.read();  // 0x41 (TEMP_OUT_H) & 0x42 (TEMP_OUT_L)
  return Tmp/340.00+36.53;
}

String getSpeed(){
  Wire.requestFrom(8, 11);    // request 11 bytes from slave device #8

  char recived[12];
  int count = 0;
  while (Wire.available()) { // slave may send less than requested
    char c = Wire.read(); // receive a byte as character
    recived[count] = c;
    count++;
  }
  recived[count] = '|';
  
  String toReturn(recived);
  
  //toReturn = toReturn +"|";
  //Serial.println(toReturn);
  return toReturn;
}
