package koopez.quente.retts;

public class SportNews {
    String title, description, sourceName, url, urlToImage, publishedAt, content;

    public SportNews(String title, String description, String sourceName, String url, String urlToImage, String publishedAt, String content) {
        this.title = title;
        this.description = description;
        this.sourceName = sourceName;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
    public String getContent() {
        return content;
    }
}
