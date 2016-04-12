# WeaRemote
WeaRemote is a framework that allows you to easily use an Android Wear watch as a remote controller.

<b>In your Wear project</b>
* Add a dependency to the <b>wearemote project</b>
* Extend <b>SensorActivity</b>
* Add the desired sensors
```
addSensor("my_accelerometer", Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
```
* Override method <b>onGotSensorData</b>
```
@Override
protected void onGotSensorData(WearSensor sensor, SensorEvent event)
{
  if (sensor.getName().equals("my_accelerometer"))
    sendSensorData("accelerometer_data_Y", event.values[1]);
}
```

<b>In your Mobile project</b>
* Add a dependency to the <b>wearemote project</b>
* Extend <b>ReceiverActivity</b>
* Override method <b>OnReceivedRemoteValue</b>
```
@Override
protected void OnReceivedRemoteValue(String type, float value)
{
  if (type.equals("accelerometer_data_Y")
    moveSpaceship(value);
}
```
