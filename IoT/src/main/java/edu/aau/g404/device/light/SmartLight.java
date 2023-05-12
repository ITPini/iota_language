package edu.aau.g404.device.light;

import edu.aau.g404.device.SmartDevice;

/**
 * SmartLight is an interface representing a specific type of SmartDevice: a smart light.
 * Classes that implement this interface should model the behavior and properties of smart lights.
 * SmartLight extends the SmartDevice interface and adds methods for controlling the light's state.
 */
public interface SmartLight extends SmartDevice {
    /**
     * Sets the on/off state of the SmartLight.
     * @param bool  True if the light should be turned on, false if it should be turned off.
     * @return      The SmartLight instance with the updated on/off state.
     */
    SmartLight isOn(boolean bool);

    /**
     * Sets the brightness of the SmartLight.
     * @param brightness    Brightness value between 0 and 1, representing the desired brightness level.
     * @return              The SmartLight instance with the updated brightness level.
     */
    SmartLight setBrightness(float brightness);

    /**
     * Sets the color of the SmartLight using RGB values.
     * @param red   Red an integer between 0 and 255.
     * @param green Green an integer between 0 and 255.
     * @param blue  Blue an integer between 0 and 255.
     * @return      The SmartLight instance with the updated color.
     */
    SmartLight setColor(int red, int green, int blue);
}