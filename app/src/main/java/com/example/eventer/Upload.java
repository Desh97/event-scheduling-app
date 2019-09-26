package com.example.eventer;

import com.google.firebase.database.Exclude;

public class Upload {
    private String event_name;
    private String imageView;
    private String category;
    private String date;
    private String time;
    private String venue;
    private String entrance;
    private String organizer;
    private String description;
    private String contact;

    private String mKey;

    public Upload(){

    }

    public Upload(String event_name,String imageView,String category,String date,String time,String venue,String entrance,String organizer,String description,String contact){
        if(event_name.trim().equals("")){
            event_name = "No Info";
        }
        if(category.trim().equals("")){
            category = "No Info";
        }
        if(date.trim().equals("")){
            date = "No Info";
        }
        if(time.trim().equals("")){
            time = "No Info";
        }
        if(venue.trim().equals("")){
            venue = "No Info";
        }
        if(entrance.trim().equals("")){
            entrance = "No Info";
        }
        if(organizer.trim().equals("")){
            organizer = "No Info";
        }
        if(description.trim().equals("")){
            description = "No Info";
        }
        if(contact.trim().equals("")){
            contact = "No Info";
        }

        this.event_name = event_name;
        this.imageView = imageView;
        this.category = category;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.entrance = entrance;
        this.organizer = organizer;
        this.description = description;
        this.contact = contact;

    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Exclude
    public String getKey(){
        return mKey;
    }
    @Exclude
    public void setKey(String key){
        mKey = key;
    }
}

