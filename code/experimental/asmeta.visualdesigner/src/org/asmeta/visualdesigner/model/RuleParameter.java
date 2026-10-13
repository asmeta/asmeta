package org.asmeta.visualdesigner.model;

public class RuleParameter {

    private String name = "";
    private String domain = "";

    public RuleParameter() {}

    public RuleParameter(String name, String domain) {
        setName(name);
        setDomain(domain);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String newName = name != null ? name.trim() : "";

        if (newName.startsWith("$")) {
            newName = newName.substring(1).trim();
        }

        this.name = newName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain != null ? domain.trim() : "";
    }
}