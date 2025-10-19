package in.bellaryinfotech.model;

import java.util.Arrays;
import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;      // image name
    private String type;      // image type
    @Lob
    private byte[] imageData; // 🖼️ image bytes

    private String videoName;     // 🎥 video name
    private String videoType;     // 🎥 video MIME type
    @Lob
    private byte[] videoData;     // 🎥 video bytes

    private String title;
    private String location;
    private String area;          // sq.ft
    private String areaInCents;   // 🆕 new field
    private String price;
    private String features;

    public FileEntity() {}

    public FileEntity(String name, String type, byte[] imageData,
                      String videoName, String videoType, byte[] videoData,
                      String title, String location, String area, String areaInCents,
                      String price, String features) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
        this.videoName = videoName;
        this.videoType = videoType;
        this.videoData = videoData;
        this.title = title;
        this.location = location;
        this.area = area;
        this.areaInCents = areaInCents;
        this.price = price;
        this.features = features;
    }

    // ✅ Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public byte[] getImageData() { return imageData; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }

    public String getVideoName() { return videoName; }
    public void setVideoName(String videoName) { this.videoName = videoName; }

    public String getVideoType() { return videoType; }
    public void setVideoType(String videoType) { this.videoType = videoType; }

    public byte[] getVideoData() { return videoData; }
    public void setVideoData(byte[] videoData) { this.videoData = videoData; }

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

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", area='" + area + '\'' +
                ", areaInCents='" + areaInCents + '\'' +
                ", price='" + price + '\'' +
                ", features='" + features + '\'' +
                '}';
    }
}
