package edu.aau.g404.device;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Light {
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

    public Light() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public Light setMetaData(MetaData metaData) {
        this.metaData = metaData;
        return this;
    }

    public On getOnState() {
        return onState;
    }

    public Light setOnState(On onState) {
        this.onState = onState;
        return this;
    }

    public Dimming getDimming() {
        return dimming;
    }

    public Light setDimming(Dimming dimming) {
        this.dimming = dimming;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public Light setColor(Color color) {
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

        public Light.MetaData setName(String name) {
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

        public Light.On setOn(boolean on) {
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

        public Light.Dimming setBrightness(float brightness) {
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






