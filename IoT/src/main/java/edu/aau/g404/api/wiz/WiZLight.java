package edu.aau.g404.api.wiz;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.aau.g404.device.light.SmartLight;

// TODO: Review getters and setters

/**
 * Represents a WiZLight object that implements the SmartLight interface.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WiZLight implements SmartLight {
    private int id = 1;
    private String method = "setState";
    private Params params = new Params();

    public WiZLight() {

    }

    @Override
    public SmartLight isOn(boolean bool) {
        this.params.state = bool;
        return this;
    }

    @Override
    public SmartLight setBrightness(float brightness) {
        this.params.dimming = (int) brightness; // Pretty sure WiZ only supports integers
        return this;
    }

    @Override
    public SmartLight setColor(int red, int green, int blue) {
        this.params.r = red;
        this.params.g = green;
        this.params.b = blue;
        return this;
    }

    @JsonCreator
    public WiZLight(@JsonProperty("id") int id, @JsonProperty("method") String method, @JsonProperty("params") Params params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public Params getParams() {
        return params;
    }

    public WiZLight setId(int id) {
        this.id = id;
        return this;
    }

    public WiZLight setMethod(String method) {
        this.method = method;
        return this;
    }

    public WiZLight setParams(Params params) {
        this.params = params;
        return this;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Params {
        private int r = 0;
        private int g = 0;
        private int b = 0;
        private int dimming = 100;
        private boolean state = true;

        public Params() {
        }

        @JsonCreator
        public Params(@JsonProperty("r") int r, @JsonProperty("g") int g, @JsonProperty("b") int b, @JsonProperty("dimming") int dimming, @JsonProperty("state") boolean state) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.dimming = dimming;
            this.state = state;
        }

        public int getR() {
            return r;
        }

        public int getG() {
            return g;
        }

        public int getB() {
            return b;
        }

        public int getDimming() {
            return dimming;
        }

        public boolean getState() {
            return state;
        }

        public Params setR(int r) {
            this.r = r;
            return this;
        }

        public Params setG(int g) {
            this.g = g;
            return this;
        }

        public Params setB(int b) {
            this.b = b;
            return this;
        }

        public Params setDimming(int dimming) {
            this.dimming = dimming;
            return this;
        }

        public Params setState(boolean state) {
            this.state = state;
            return this;
        }
    }
}