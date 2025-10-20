package in.bellaryinfotech.dto;

import in.bellaryinfotech.model.FileEntity;

public class FileDTO {

    private Long id;

    // 🖼️ Image details
    private String name;
    private String type;
    private String imageUrl;

    // 🎥 Video details
    private String videoName;
    private String videoType;
    private String videoUrl;

    // 🏡 Property details
    private String title;
    private String location;
    private String area;
    private String areaInCents;
    private String price;
    private String features;

    public FileDTO() {}

    public FileDTO(Long id, String name, String type, String imageUrl,
                   String videoName, String videoType, String videoUrl,
                   String title, String location, String area, String areaInCents,
                   String price, String features) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.videoName = videoName;
        this.videoType = videoType;
        this.videoUrl = videoUrl;
        this.title = title;
        this.location = location;
        this.area = area;
        this.areaInCents = areaInCents;
        this.price = price;
        this.features = features;
    }
    
    public FileDTO(FileEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.type = entity.getType();
        this.imageUrl = entity.getImageUrl();
        this.videoName = entity.getVideoName();
        this.videoType = entity.getVideoType();
        this.videoUrl = entity.getVideoUrl();
        this.title = entity.getTitle();
        this.location = entity.getLocation();
        this.area = entity.getArea();
        this.areaInCents = entity.getAreaInCents();
        this.price = entity.getPrice();
        this.features = entity.getFeatures();
    }


    // ✅ Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getVideoName() { return videoName; }
    public void setVideoName(String videoName) { this.videoName = videoName; }

    public String getVideoType() { return videoType; }
    public void setVideoType(String videoType) { this.videoType = videoType; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getAreaInCents() { return areaInCents; }
    public void setAreaInCents(String areaInCents) { this.areaInCents = areaInCents; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }
}
