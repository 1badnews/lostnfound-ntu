package com.example.lostandfound;

public class Items
{
    private String Title;
    private String Description;
    private String Contacts;
    private String User;
    private String Image;

 public Items() {}

    public Items(String title, String description, String contacts, String user, String image) {
        Title = title;
        Description = description;
        Contacts = contacts;
        User = user;
        Image = image;
    }


    public String getUser()
    {
        return User;
    }

    public void setUser(String user)
    {
        User = user;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getContacts() {
        return Contacts;
    }

    public void setContacts(String contacts) {
        Contacts = contacts;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
