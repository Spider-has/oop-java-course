package org.example;

public class App {
    public static void main(String[] args) throws Exception {
        Data data = new Data();
        SaveData.save(data, "output.json", "output.log");
    }
}
