import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Cesar {
    public void registerMenu(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (true){
            if(Pattern.matches("register .+ .+", input)){
                if(!Pattern.matches("([a-z]|[0-9]|_)+",input.split(" ")[1]))
                    System.out.println("username format is invalid");
                else if(!Pattern.matches("([a-z]|[0-9]|_)+",input.split(" ")[2]))
                    System.out.println("password format is invalid");
                else if(Person.getPersonByUserName(input.split(" ")[1]) != null)
                    System.out.println("a user exists with this username");
                else {
                    Person.addPerson(new Person(input.split(" ")[1], input.split(" ")[2]));
                    System.out.println("register successful");
                }
            }
            else if(Pattern.matches("login .+ .+", input)){
                if(!Pattern.matches("([a-z]|[0-9]|_)+",input.split(" ")[1]))
                    System.out.println("username format is invalid");
                else if(!Pattern.matches("([a-z]|[0-9]|_)+",input.split(" ")[2]))
                    System.out.println("password format is invalid");
                else if(Person.getPersonByUserName(input.split(" ")[1]) == null)
                    System.out.println("no user exists with this username");
                else if(!Person.getPersonByUserName(input.split(" ")[1]).getPass().equals(input.split(" ")[2]))
                    System.out.println("incorrect password");
                else{
                    System.out.println("login successful");
                    mainMenu(Person.getPersonByUserName(input.split(" ")[1]));
                }
            }
            else if(Pattern.matches("remove .+ .+", input)){
                if(!Pattern.matches("([a-z]|[0-9]|_)+",input.split(" ")[1]))
                    System.out.println("username format is invalid");
                else if(!Pattern.matches("([a-z]|[0-9]|_)+",input.split(" ")[2]))
                    System.out.println("password format is invalid");
                else if(Person.getPersonByUserName(input.split(" ")[1]) == null)
                    System.out.println("no user exists with this username");
                else if(!Person.getPersonByUserName(input.split(" ")[1]).getPass().equals(input.split(" ")[2]))
                    System.out.println("incorrect password");
                else{
                    Person.getPeople().remove(Person.getPersonByUserName(input.split(" ")[1]));
                    System.out.println("removed" +  input.split(" ")[1] + "successfully");
                }
            }
            else if(input.equals("list_users")){
                int n = Person.getPeople().size();
                for (int i = 0; i < n - 1; i++)
                    for (int j = 0; j < n - i - 1; j++) {
                        if (Person.getPeople().get(j).getUserName().compareToIgnoreCase(Person.getPeople().get(j + 1).getUserName()) < 0) {
                            Person.getPeople().add(j, Person.getPeople().get(j + 1));
                            Person.getPeople().remove(j + 2);
                        }
                    }
                for (int i = n - 1; i >= 0 ; i--) {
                    System.out.println(Person.getPeople().get(i).getUserName());
                }
            }
            else if(input.equals("help")){
                System.out.println("register [username] [password]\n" +
                        "login [username] [password]\n" +
                        "remove [username] [password]\n" +
                        "list_users\n" +
                        "help\n" +
                        "exit");
            }
        }
    }
    public void mainMenu(Person person){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if(Pattern.matches(""))
    }
}
class Person{
    public static ArrayList<Person> people = new ArrayList<>();
    private String userName;
    private String pass;

    public Person(String userName, String pass){
        this.userName = userName;
        this.pass = pass;
    }

    public static ArrayList<Person> getPeople() {
        return people;
    }

    public String getUserName() {
        return userName;
    }

    public String getPass() {
        return pass;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public static void addPerson(Person person){
        people.add(person);
    }

    public static Person getPersonByUserName(String userName){
        int n = people.size();
        for (int i = 0; i < n; i++) {
            if (people.get(i).userName.equals(userName))
                return people.get(i);
        }
        return null;
    }
}