package br.com.master.sonar;

import java.util.List;

public class Component {
    private String id;
    private String key;
    private String name;
    private String qualifier;
    private String path;
    private String language;
    private List<Measure> measures;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    @Override
    public String toString() {
        return "Component{" + "id='" + id + '\'' + ", key='" + key + '\'' + ", name='" + name + '\'' + ", qualifier='" + qualifier + '\'' + ", path='" + path + '\'' + ", language='" + language + '\'' +
               ", measures=" + measures + '}';
    }
}
