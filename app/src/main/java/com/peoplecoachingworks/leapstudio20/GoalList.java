package com.peoplecoachingworks.leapstudio20;

/**
 * Created by Duncan on 08-Mar-18.
 */

public class GoalList {
    private int id;
    private String goals;

    //Constructor
    public GoalList(int id, String goals) {
        this.id = id;
        this.goals = goals;
    }

    //Setter, getter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }
}
