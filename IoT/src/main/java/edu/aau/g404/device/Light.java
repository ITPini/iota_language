package edu.aau.g404.device;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public Light() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public On getOnState() {
        return onState;
    }

    public void setOnState(On onState) {
        this.onState = onState;
    }

    public Dimming getDimming() {
        return dimming;
    }

    public void setDimming(Dimming dimming) {
        this.dimming = dimming;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class MetaData {
        @JsonCreator
        public MetaData(){

        }
        @JsonProperty("name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public void setOn(boolean on) {
            this.on = on;
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

        public void setBrightness(float brightness) {
            this.brightness = brightness;
        }
    }
}






