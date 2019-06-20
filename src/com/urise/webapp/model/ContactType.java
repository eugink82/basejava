package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype"){
        @Override
        public String toHtml(String value){
            return "<a href='skype:"+value+"'>"+value+"</a>";
        }
    },
    EMAIL("Эл.почта"){
        @Override
        public String toHtml(String value){
            return "<a href='mailto:"+value+"'>"+value+"</a>";
        }
    },
    PROFILE("Профиль в соц.сети"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Github"),
    STACKOVERFLOW("StackOverFlow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value){
        return title+": "+value;
    }

    public String toHtml(String value){
        return (value==null) ? "": toHtml0(value);
    }
}
