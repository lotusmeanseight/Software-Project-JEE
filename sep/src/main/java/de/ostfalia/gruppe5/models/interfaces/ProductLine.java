package de.ostfalia.gruppe5.models.interfaces;

import java.io.Serializable;

public interface ProductLine extends Serializable {
    String getProductLine();

    void setProductLine(String productLine);

    String getTextDescription();

    void setTextDescription(String textDescription);

    String getHtmlDescription();

    void setHtmlDescription(String htmlDescription);

    Serializable getBLOB();

    void setBLOB(Serializable bLOB);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
