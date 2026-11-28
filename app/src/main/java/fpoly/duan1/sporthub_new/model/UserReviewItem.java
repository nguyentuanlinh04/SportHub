package fpoly.duan1.sporthub_new.model;

public class UserReviewItem {
    private String id;
    private String pitchName;
    private String matchDetails;
    private String scoreText;
    private String tagText;
    private String commentText;
    private String createdDate;
    private int starCount;

    public UserReviewItem(String id, String pitchName, String matchDetails, String scoreText,
                          String tagText, String commentText, String createdDate, int starCount) {
        this.id = id;
        this.pitchName = pitchName;
        this.matchDetails = matchDetails;
        this.scoreText = scoreText;
        this.tagText = tagText;
        this.commentText = commentText;
        this.createdDate = createdDate;
        this.starCount = starCount;
    }

    public String getId() {
        return id;
    }

    public String getPitchName() {
        return pitchName;
    }

    public String getMatchDetails() {
        return matchDetails;
    }

    public String getScoreText() {
        return scoreText;
    }

    public String getTagText() {
        return tagText;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public int getStarCount() {
        return starCount;
    }
}
