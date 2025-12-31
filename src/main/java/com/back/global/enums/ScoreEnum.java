package com.back.global.enums;


/**
 * 점수 타입과 타입별 점수 수치를 기록하는 enum
 * 이걸 그냥 각 기능별 로직에서 불러써도 되긴 하는데
 * 그러면 여러 기능을 횡단하게 되어버린다. 책임이 분산됨...
 * BoundedContext 안에 점수 관리를 맡는 별도의 Score 모듈을 만든다고 치고, 이 Enum은 Common에 유지?
 */
public enum ScoreEnum {
    POST_CREATE(3),
    COMMENT_CREATE(1);

    private final int score;

    public int getScore(){
        return score;
    }

    ScoreEnum (int score){
        this.score=score;
    }

}
