public class Game {
    private final String title;
    private final String achievements;
    private final String info;
    private final String technicalData;
    Game(String _title, String _achievements, String _info, String _technical_data){
        title=_title;
        achievements=_achievements;
        info=_info;
        technicalData =_technical_data;
    }

    public String getAchievements() {
        return achievements;
    }

    public String getInfo() {
        return info;
    }

    public String getTechnicalData() {
        return technicalData;
    }

    public String getTitle() {
        return title;
    }
}
