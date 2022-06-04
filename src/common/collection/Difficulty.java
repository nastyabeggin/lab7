package common.collection;

public enum Difficulty {
    VERY_EASY("VERY_EASY"),
    NORMAL("NORMAL"),
    IMPOSSIBLE("IMPOSSIBLE");
    private String difficultyOfLab;

    Difficulty(String difficultyOfLab) {
        this.setDifficultyOfLab(difficultyOfLab);
    }

    public String getDifficultyOfLab() {
        return difficultyOfLab;
    }

    public void setDifficultyOfLab(String difficultyOfLab) {
        this.difficultyOfLab = difficultyOfLab;
    }
}
