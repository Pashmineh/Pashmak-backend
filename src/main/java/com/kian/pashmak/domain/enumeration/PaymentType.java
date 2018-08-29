package com.kian.pashmak.domain.enumeration;

/**
 * The PaymentType enumeration.
 */
public enum PaymentType {
    TAKHIR("تاخیر"), SHIRINI("شیرینی"), JALASE("جلسه");
private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    PaymentType(String title) {
        this.title=title;
    }
}
