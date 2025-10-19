package in.bellaryinfotech.dto;

public class FileDTO {

    private Long id;
    private String name;
    private String type;
    private String videoType;
    private String url;
    private String title;
    private String location;
    private String area;
    private String areaInCents;
    private String price;
    private String features;

    // For backward compatibility (if needed)
    private String base64Data;

    // New fields for image and video Base64
    private String base64Image;
    private String base64Video;

    private String downloadUrl;

    // Default constructor
    public FileDTO() {}

    // Constructor for upload response
    public FileDTO(Long id, String name, String type, String url) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
    }

    public FileDTO(Long id, String name, String type, String title, String location,
                   String area, String price, String features, String base64Data) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.title = title;
        this.location = location;
        this.area = area;
        this.price = price;
        this.features = features;
        this.base64Data = base64Data;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaInCents() {
        return areaInCents;
    }

    public void setAreaInCents(String areaInCents) {
        this.areaInCents = areaInCents;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getBase64Video() {
        return base64Video;
    }

    public void setBase64Video(String base64Video) {
        this.base64Video = base64Video;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
