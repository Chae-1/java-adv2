package reflection.data;

public class User {
    private Integer age;
    private String name;
    private String id;

    public User(Integer age, String name, String id) {
        this.age = age;
        this.name = name;
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
