package com.bdjobs.currencycalculator;

import java.util.Date;

public class JsonVat {

    private String countryName;
    private String countryCode;
    private Date effectiveDate;
    private double reducedRate;
    private double superReucedRate;
    private double standardRate;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public double getReducedRate() {
        return reducedRate;
    }

    public void setReducedRate(double reducedRate) {
        this.reducedRate = reducedRate;
    }

    public double getSuperReucedRate() {
        return superReucedRate;
    }

    public void setSuperReucedRate(double superReucedRate) {
        this.superReucedRate = superReucedRate;
    }

    public double getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(double standardRate) {
        this.standardRate = standardRate;
    }
}
