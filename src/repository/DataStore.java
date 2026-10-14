package repository;

import model.Admin;
import model.Question;
import model.Quiz;
import model.Result;
import model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * In-Memory Central Data Store.
 * 
 * Demonstrates Collections Framework:
 * 1. ArrayList:
 *    - ArrayList<Student> maintains sequential list of registered students.
 *    - ArrayList<Admin> maintains registered administrators.
 *    - ArrayList<Question> stores the global pool of questions.
 *    - ArrayList<Quiz> stores all created quizzes.
 *    - ArrayList<Result> stores the complete examination history log.
 * 
 * 2. HashMap:
 *    - HashMap<Integer, Question> provides constant O(1) time lookup for questions by their Question ID.
 *    - HashMap<Integer, Quiz> provides constant O(1) time lookup for quizzes by their Quiz ID.
 * 
 * 3. HashSet:
 *    - HashSet<String> enforces uniqueness of usernames across the system in O(1) time.
 */
public class DataStore {

    // ArrayList maintains sequential lists of entities
    public static ArrayList<Student> students = new ArrayList<>();
    public static ArrayList<Admin> admins = new ArrayList<>();
    public static ArrayList<Question> questions = new ArrayList<>();
    public static ArrayList<Quiz> quizzes = new ArrayList<>();
    public static ArrayList<Result> results = new ArrayList<>();

    // HashMap provides fast O(1) lookup using Integer IDs as keys
    public static HashMap<Integer, Question> questionMap = new HashMap<>();
    public static HashMap<Integer, Quiz> quizMap = new HashMap<>();

    // HashSet guarantees unique usernames across the entire application
    public static HashSet<String> usernames = new HashSet<>();

    /**
     * Pre-populates sample data into collections for instant testing.
     * Everything resides purely in RAM.
     */
    public static void initSampleData() {
        // Preload Admin
        Admin defaultAdmin = new Admin(1, "System Administrator", "admin", "admin123", "Computer Science Dept");
        admins.add(defaultAdmin);
        usernames.add("admin");

        // Preload Student
        Student defaultStudent = new Student(101, "Sarthak", "sarthak", "pass123", "MCA");
        students.add(defaultStudent);
        usernames.add("sarthak");

        // Preload Questions into ArrayList and HashMap
        addSampleQuestion(new Question(
                101,
                "Which keyword is used for inheritance in Java?",
                "implements",
                "extends",
                "inherits",
                "super",
                'B'
        ));

        addSampleQuestion(new Question(
                102,
                "Which collection class guarantees unique elements?",
                "ArrayList",
                "LinkedList",
                "HashSet",
                "Vector",
                'C'
        ));

        addSampleQuestion(new Question(
                103,
                "Which keyword prevents a class from being inherited in Java?",
                "static",
                "final",
                "abstract",
                "const",
                'B'
        ));

        addSampleQuestion(new Question(
                104,
                "Which of the following is the root superclass of all Exceptions and Errors?",
                "Exception",
                "RuntimeException",
                "Throwable",
                "Error",
                'C'
        ));

        addSampleQuestion(new Question(
                105,
                "Which block is always executed whether an exception is handled or not?",
                "try",
                "catch",
                "finally",
                "throw",
                'C'
        ));

        addSampleQuestion(new Question(
                106,
                "Which Map implementation allows null keys in Java?",
                "TreeMap",
                "Hashtable",
                "HashMap",
                "ConcurrentHashMap",
                'C'
        ));

        addSampleQuestion(new Question(
                107,
                "What is the return type of the hashCode() method in Java?",
                "int",
                "long",
                "void",
                "boolean",
                'A'
        ));

        addSampleQuestion(new Question(
                108,
                "Which core collection interface does NOT inherit from java.util.Collection?",
                "List",
                "Set",
                "Map",
                "Queue",
                'C'
        ));

        addSampleQuestion(new Question(
                109,
                "Which of the following is an unchecked exception in Java?",
                "IOException",
                "SQLException",
                "ClassNotFoundException",
                "NullPointerException",
                'D'
        ));

        addSampleQuestion(new Question(
                110,
                "Can an abstract class in Java have a constructor?",
                "Yes, it can have constructors",
                "No, abstract classes cannot have constructors",
                "Only if the constructor is static",
                "Only if the constructor is private",
                'A'
        ));

        addSampleQuestion(new Question(
                111,
                "Which utility method is commonly used to sort an ArrayList in Java?",
                "Collections.sort()",
                "Arrays.sort()",
                "List.sortOnly()",
                "Collection.arrange()",
                'A'
        ));

        addSampleQuestion(new Question(
                112,
                "What is the default initial capacity of a standard ArrayList in Java?",
                "5",
                "10",
                "16",
                "20",
                'B'
        ));

        addSampleQuestion(new Question(
                113,
                "Which keyword is used to explicitly throw an exception object in Java?",
                "throws",
                "throw",
                "try",
                "catch",
                'B'
        ));

        addSampleQuestion(new Question(
                114,
                "Which Set implementation maintains elements in ascending sorted order?",
                "HashSet",
                "LinkedHashSet",
                "TreeSet",
                "VectorSet",
                'C'
        ));

        addSampleQuestion(new Question(
                115,
                "In Java, can an interface extend multiple other interfaces?",
                "Yes, using the 'extends' keyword",
                "No, interfaces only allow single inheritance",
                "Only if all methods are static",
                "Only if using 'implements'",
                'A'
        ));

        // Preload Sample Quiz 1: Java Fundamentals
        Quiz sampleQuiz1 = new Quiz(1, "Java Fundamentals");
        sampleQuiz1.addQuestion(questionMap.get(101));
        sampleQuiz1.addQuestion(questionMap.get(102));
        sampleQuiz1.addQuestion(questionMap.get(103));
        sampleQuiz1.addQuestion(questionMap.get(104));
        sampleQuiz1.addQuestion(questionMap.get(105));
        sampleQuiz1.addQuestion(questionMap.get(106));

        quizzes.add(sampleQuiz1);
        quizMap.put(sampleQuiz1.getQuizId(), sampleQuiz1);

        // Preload Sample Quiz 2: Java OOP & Collections Mastery
        Quiz sampleQuiz2 = new Quiz(2, "Java OOP & Collections Mastery");
        sampleQuiz2.addQuestion(questionMap.get(107));
        sampleQuiz2.addQuestion(questionMap.get(108));
        sampleQuiz2.addQuestion(questionMap.get(109));
        sampleQuiz2.addQuestion(questionMap.get(110));
        sampleQuiz2.addQuestion(questionMap.get(111));
        sampleQuiz2.addQuestion(questionMap.get(112));
        sampleQuiz2.addQuestion(questionMap.get(113));
        sampleQuiz2.addQuestion(questionMap.get(114));
        sampleQuiz2.addQuestion(questionMap.get(115));

        quizzes.add(sampleQuiz2);
        quizMap.put(sampleQuiz2.getQuizId(), sampleQuiz2);
    }

    private static void addSampleQuestion(Question q) {
        questions.add(q);
        questionMap.put(q.getQuestionId(), q);
    }
}
