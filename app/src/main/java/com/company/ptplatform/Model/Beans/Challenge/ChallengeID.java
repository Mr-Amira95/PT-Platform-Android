package com.company.ptplatform.Model.Beans.Challenge;

import java.util.ArrayList;

public class ChallengeID {

    Integer coach_id;
    ArrayList<Integer> challenge_video_ids;

    public ChallengeID(Integer coach_id, ArrayList<Integer> challenge_video_ids) {
        this.coach_id = coach_id;
        this.challenge_video_ids = challenge_video_ids;
    }

}
