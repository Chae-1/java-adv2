package annotation.validator;

public class Team {
    @NotEmpty(message = "이름이 비었습니다.")
    private String name;

    @Range(min = 1, max = 999, message = "1부터 999까지 갖을 수 있습니다.")
    private int memberCount;

    public Team(String name, int memberCount) {
        this.name = name;
        this.memberCount = memberCount;
    }

    public String getName() {
        return name;
    }

    public int getMemberCount() {
        return memberCount;
    }
}
