package edu.aau.g404.api.hue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.aau.g404.device.SmartLight;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HueLight extends SmartLight {
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

    @Override
    public SmartLight isOn(boolean bool) {
        this.onState.setOn(bool);
        return this;
    }

    @Override
    public SmartLight setBrightness(float brightness) {
        this.dimming.brightness = brightness;
        return this;
    }

    @Override
    public SmartLight setColor(int red, int green, int blue) {
        double[] xy = rgbToXY(red, green, blue);
        this.color.setX(xy[0]);
        this.color.setY(xy[1]);
        return this;
    }

    // Source: https://github.com/Koenkk/zigbee2mqtt/issues/272
    public double[] rgbToXY(int r, int g, int b) {
        // Convert RGB to linear RGB
        double linearR = (r / 255.0) <= 0.04045 ? (r / 255.0) / 12.92 : Math.pow(((r / 255.0) + 0.055) / 1.055, 2.4);
        double linearG = (g / 255.0) <= 0.04045 ? (g / 255.0) / 12.92 : Math.pow(((g / 255.0) + 0.055) / 1.055, 2.4);
        double linearB = (b / 255.0) <= 0.04045 ? (b / 255.0) / 12.92 : Math.pow(((b / 255.0) + 0.055) / 1.055, 2.4);

        // Convert linear RGB to XYZ
        // Source: http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
        double x = linearR * 0.664511 + linearG * 0.154324 + linearB * 0.162028;
        double y = linearR * 0.283881 + linearG * 0.668433 + linearB * 0.047685;
        double z = linearR * 0.000088 + linearG * 0.072310 + linearB * 0.986039;

        // Calculate normalized chromaticity coordinates x and y
        double cieX = x / (x + y + z);
        double cieY = y / (x + y + z);

        return new double[]{cieX, cieY};
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
