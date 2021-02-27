package com.example.rws.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompilerModels {

    @SerializedName("output")
    @Expose
    String output;
    @SerializedName("statusCode")
        @Expose
        String statusCode;
    @SerializedName("memory")
        @Expose
        String memory;
    @SerializedName("cpuTime")
        @Expose
        String cpuTime;
    @SerializedName("clientId")
            @Expose
            String clientId;
    @SerializedName("clientSecret")
            @Expose
            String clientSecret;
    @SerializedName("script")
            @Expose
            String script;
    @SerializedName("stdin")
            @Expose
            String stdin;
    @SerializedName("language")
            @Expose
            String language;
    @SerializedName("versionIndex")
            @Expose
            String versionIndex;

    public CompilerModels(String clientId, String clientSecret, String script, String stdin, String language, String versionIndex) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.script = script;
        this.stdin = stdin;
        this.language = language;
        this.versionIndex = versionIndex;
    }

    public String getOutput() {
        return output;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMemory() {
        return memory;
    }

    public String getCpuTime() {
        return cpuTime;
    }
}
