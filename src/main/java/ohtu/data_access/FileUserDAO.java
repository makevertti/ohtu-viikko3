package ohtu.data_access;

import ohtu.domain.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUserDAO implements UserDao {

    private File file;
    private Scanner scanner;
    private FileWriter fileWriter;
    private String filename;

    public FileUserDAO(String filename) {
        file = new File(filename);
        this.filename = filename;
        createScanner(filename);
        try {
            fileWriter = new FileWriter(filename, true);
        } catch (IOException e) {
            System.out.println("Error while opening user data file");
        }
    }

    private void createScanner(String filename) {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("User data file: '" + filename + "' created");
        }
    }

    @Override
    public List<User> listAll() {
        createScanner(filename);
        List<User> userList = new ArrayList<User>();
        while (scanner.hasNext()) {
            userList.add(new User(scanner.next(), scanner.next()));
        }
        return userList;
    }

    @Override
    public User findByName(String name) {
        createScanner(filename);
        while (scanner.hasNext()) {
            String readName = scanner.next();
            String readPassword = scanner.next();
            if (readName == name)
                return new User(readName, readPassword);
        }
        return null;
    }

    @Override
    public void add(User user) {
        try {
            fileWriter.append(user.getUsername() + " " + user.getPassword() + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println("Error writing user data");
        }
    }
}
