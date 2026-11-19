package fpoly.duan1.sporthub_new.model;

public class MatchTeam {
    private String id;
    private String teamName;
    private String captainName;
    private String distance;
    private String levelText;
    private String timeText;
    private String pitchType;
    private String splitFee;
    private String avatarText;
    private int avatarBgRes;
    private int levelBgRes;
    private int levelTextColor;

    public MatchTeam(String id, String teamName, String captainName, String distance, String levelText,
                     String timeText, String pitchType, String splitFee, String avatarText,
                     int avatarBgRes, int levelBgRes, int levelTextColor) {
        this.id = id;
        this.teamName = teamName;
        this.captainName = captainName;
        this.distance = distance;
        this.levelText = levelText;
        this.timeText = timeText;
        this.pitchType = pitchType;
        this.splitFee = splitFee;
        this.avatarText = avatarText;
        this.avatarBgRes = avatarBgRes;
        this.levelBgRes = levelBgRes;
        this.levelTextColor = levelTextColor;
    }

    public String getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getCaptainName() {
        return captainName;
    }

    public String getDistance() {
        return distance;
    }

    public String getLevelText() {
        return levelText;
    }

    public String getTimeText() {
        return timeText;
    }

    public String getPitchType() {
        return pitchType;
    }

    public String getSplitFee() {
        return splitFee;
    }

    public String getAvatarText() {
        return avatarText;
    }

    public int getAvatarBgRes() {
        return avatarBgRes;
    }

    public int getLevelBgRes() {
        return levelBgRes;
    }

    public int getLevelTextColor() {
        return levelTextColor;
    }
}
