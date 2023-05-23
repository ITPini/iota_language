package edu.aau.g404.CodeGenerator;

public class DeviceData {
    private String deviceName;
    private String deviceType;
    private String deviceBrand;
    private String deviceIdentifier;

    public DeviceData(String deviceName, String deviceType, String deviceBrand, String deviceIdentifier) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.deviceBrand = deviceBrand;
        this.deviceIdentifier = deviceIdentifier;
    }

    public DeviceData(){

    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "deviceName='" + deviceName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceBrand='" + deviceBrand + '\'' +
                ", deviceIdentifier='" + deviceIdentifier + '\'' +
                '}';
    }
}
