package edu.aau.g404.api.hue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.aau.g404.device.light.SmartLight;

import java.util.HashMap;
import java.util.Map;

// TODO: Review getters and setters

/**
 * Represenst a HueLight object that implements the SmartLight interface.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class HueLight implements SmartLight {
    @JsonProperty("id")
    private String identifier;

    @JsonProperty("metadata")
    private MetaData metaData;

    @JsonProperty("on")
    private On onState;

    @JsonProperty("dimming")
    private Dimming dimming;

    @JsonProperty("color")
    private Color color;

    public HueLight() {
    }

    @Override @JsonIgnore
    public SmartLight changeOnState(boolean bool) {
        this.onState = new On();
        this.onState.on = bool;
        return this;
    }

    @Override @JsonIgnore
    public SmartLight setBrightness(float brightness) {
        this.dimming = new Dimming();
        this.dimming.brightness = brightness;
        return this;
    }

    @Override @JsonIgnore
    public SmartLight setColors(int red, int green, int blue) {
        this.color = new Color();
        double[] xy = rgbToXY(red, green, blue);
        this.color.setX(xy[0]);
        this.color.setY(xy[1]);
        return this;
    }

    @Override @JsonIgnore
    public boolean checkIfOn() {
        return this.onState.on;
    }

    @Override @JsonIgnore
    public float getBrightness() {
        return this.dimming.brightness;
    }

    @Override @JsonIgnore
    public int[] getColors() {
        return new int[0];
    }

    /**
     * Converts RGB color values to the CIE 1931 color space (xy) values.
     * This method uses the sRGB D65 color space matrix for conversion.
     * @param r The red component of the RGB color.
     * @param g The green component of the RGB color.
     * @param b The blue component of the RGB color.
     * @return  A double array containing the CIE 1931 color space (xy) values.
     * @see <a href="https://github.com/Koenkk/zigbee2mqtt/issues/272">JS implementation</a>
     * @see <a href="http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html">RGB to XYZ Matrices</a>
     * @see <a href="https://en.wikipedia.org/wiki/CIE_1931_color_space">XYZ to XY</a>
     */
    protected double[] rgbToXY(int r, int g, int b) {
        // RGB to linear RGB
        double linearR = toLinearRGB(r);
        double linearG = toLinearRGB(g);
        double linearB = toLinearRGB(b);

        // Color space Matrix RGB to XY (sRGB D65)
        double colorSpace[][] = {
                {0.664511, 0.154324, 0.162028},
                {0.283881, 0.668433, 0.047685},
                {0.000088, 0.072310, 0.986039}
        };

        // Linear RGB to XYZ
        double x = linearR * colorSpace[0][0] + linearG * colorSpace[0][1] + linearB * colorSpace[0][2];
        double y = linearR * colorSpace[1][0] + linearG * colorSpace[1][1] + linearB * colorSpace[1][2];
        double z = linearR * colorSpace[2][0] + linearG * colorSpace[2][1] + linearB * colorSpace[2][2];

        // Normalized chromaticity coordinates x and y
        double cieX = x / (x + y + z);
        double cieY = y / (x + y + z);

        return new double[]{cieX, cieY};
    }

    /**
     * Converts a single RGB color component to its linear RGB representation.
     * @param color The color component value (0-255) to be converted.
     * @return      The linear RGB representation of the color component.
     */
    protected double toLinearRGB(int color){
        double component = color / 255.0;
        return component <= 0.04045 ? component / 12.92 : Math.pow((component + 0.055) / 1.055, 2.4);
    }

    public String getIdentifier() {
        return identifier;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public HueLight setMetaData(MetaData metaData) {
        this.metaData = metaData;
        return this;
    }

    public On getOnState() {
        return onState;
    }

    public HueLight setOnState(On onState) {
        this.onState = onState;
        return this;
    }

    public Dimming getDimming() {
        return dimming;
    }

    public HueLight setDimming(Dimming dimming) {
        this.dimming = dimming;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public HueLight setColor(Color color) {
        this.color = color;
        return this;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetaData {
        @JsonCreator
        public MetaData(){

        }
        @JsonProperty("name")
        private String name;

        public String getName() {
            return name;
        }

        public MetaData setName(String name) {
            this.name = name;
            return this;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class On {
        @JsonCreator
        public On(){

        }
        public On(boolean on) {
            this.on = on;
        }

        @JsonProperty("on")
        private boolean on;

        public boolean isOn() {
            return on;
        }

        public On setOn(boolean on) {
            this.on = on;
            return this;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dimming {
        @JsonCreator
        public Dimming(){

        }
        public Dimming(float brightness) {
            this.brightness = brightness;
        }

        @JsonProperty("brightness")
        private float brightness;

        public float getBrightness() {
            return brightness;
        }

        public Dimming setBrightness(float brightness) {
            this.brightness = brightness;
            return this;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Color {

        @JsonCreator
        public Color(@JsonProperty("xy") Map<String, Double> xy) {
            this.xy = xy;
        }

        public Color(double x, double y){
            this.xy.put("x", x);
            this.xy.put("y", y);
        }

        public Color() {
        }

        @JsonProperty("xy")
        private Map<String, Double> xy = new HashMap<>();

        public double getX() {
            return xy.get("x");
        }

        public Color setX(double x) {
            this.xy.put("x", x);
            return this;
        }

        public double getY() {
            return xy.get("y");
        }

        public Color setY(double y) {
            this.xy.put("y", y);
            return this;
        }
    }
}