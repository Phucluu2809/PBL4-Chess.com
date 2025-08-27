package Model.BEAN;

public class UserBEAN {
    private int userId;
    private String username;
    private String password;
    private String email;
    private int winCount;
    private int loseCount;
    private int drawCount;
    private boolean onlineStatus;

    public UserBEAN() {}

    public UserBEAN(int userId, String username, String password, String email,
                    int winCount, int loseCount, int drawCount, boolean onlineStatus) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.winCount = winCount;
        this.loseCount = loseCount;
        this.drawCount = drawCount;
        this.onlineStatus = onlineStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
