package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatisticsDisplay implements Observer, DisplayElement{
    private List<Float> listTemperature;
    private float avgTemperature;
    private float minTemperature;
    private float maxTemperature;
    private Subject weatherData;

    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public String display() {
        String html = "";
        html += String.format("<div style=\"background-image: " +
                "url(/images/sky.webp); " +
                "height: 400px; " +
                "width: 647.2px;" +
                "display:flex;flex-wrap:wrap;justify-content:center;align-content:center;" +
                "\">");
        html += "<section>";
        html += String.format("<label>Average Temperature: %s</label><br />", avgTemperature);
        html += String.format("<label>Min Temperature: %s</label><br />", minTemperature);
        html += String.format("<label>Max Temperature: %s</label>", maxTemperature);
        html += "</section>";
        html += "</div>";
        return html;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        float avg = 0;
        this.listTemperature.add(temperature);
        for (int i = 0; i < listTemperature.size(); i++) {
            avg += listTemperature.get(i);
            if (listTemperature.get(i) > maxTemperature)
                this.maxTemperature = listTemperature.get(i);
            if (listTemperature.get(i) < minTemperature)
                this.minTemperature = listTemperature.get(i);
        }
        this.avgTemperature = avg / listTemperature.size();
    }

    @Override
    public String name() {
        return "Statistics Display";
    }

    @Override
    public String id() {
        return "statistics-condition";
    }

    public void subscribe() {
        weatherData.registerObserver(this);
    }

    public void unsubscribe() {
        weatherData.removeObserver(this);
    }
}