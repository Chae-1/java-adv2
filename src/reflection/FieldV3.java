package reflection;

import reflection.data.Team;
import reflection.data.User;

public class FieldV3 {
    public static void main(String[] args) {
        Team team = new Team("team1", null);
        User user = new User(null, null, "id1");
        System.out.println("===== before =====");
        System.out.println("user = " + user);
        System.out.println("team = " + team);

        if (user.getId() == null) {
            user.setId("");
        }

        if (user.getName() == null) {
            user.setName("");
        }

        if (user.getAge() == null) {
            user.setAge(0);
        }

        if (team.getId() == null) {
            team.setId("");
        }

        if (team.getName() == null) {
            team.setName("");
        }

        System.out.println("===== after =====");
        System.out.println("user = " + user);
        System.out.println("team = " + team);
    }
}
