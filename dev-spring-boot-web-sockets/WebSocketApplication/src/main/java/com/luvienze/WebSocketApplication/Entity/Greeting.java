package com.luvienze.WebSocketApplication.Entity;

public class Greeting {

    private String content;

    public Greeting(){

    }

    public Greeting(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "content='" + content + '\'' +
                '}';
    }
}
