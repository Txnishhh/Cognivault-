import java.util.*;

public class Main {


    static class Task {
        static int counter = 1;
        int id; String title, description, dueDate; boolean completed;
        Task(String title, String description, String dueDate) {
            this.id = counter++; this.title = title;
            this.description = description; this.dueDate = dueDate;
            this.completed = false;
        }
        public String toString() {
            return "[" + id + "] " + (completed ? "✓" : "✗") + " " + title +
                    " | Due: " + dueDate + " | " + description;
        }
    }

    static class Note {
        static int counter = 1;
        int id; String title, content, date;
        Note(String title, String content) {
            this.id = counter++; this.title = title;
            this.content = content; this.date = "2025-01-01";
        }
        public String toString() { return "[" + id + "] " + title; }
    }

    static class ClassEntry {
        static int counter = 1;
        int id; String subject, day, startTime, endTime;
        ClassEntry(String subject, String day, String startTime, String endTime) {
            this.id = counter++; this.subject = subject;
            this.day = day; this.startTime = startTime; this.endTime = endTime;
        }
        public String toString() {
            return "[" + id + "] " + subject + " | " + day + " | " + startTime + " - " + endTime;
        }
    }

    static class Subject {
        static int counter = 1;
        int id; String name; double marks; int credits;
        Subject(String name, double marks, int credits) {
            this.id = counter++; this.name = name;
            this.marks = marks; this.credits = credits;
        }
        String letterGrade() {
            if (marks >= 90) return "A+"; if (marks >= 80) return "A";
            if (marks >= 70) return "B";  if (marks >= 60) return "C";
            if (marks >= 50) return "D";  return "F";
        }
        double gpaPoints() {
            if (marks >= 90) return 10.0; if (marks >= 80) return 9.0;
            if (marks >= 70) return 8.0;  if (marks >= 60) return 7.0;
            if (marks >= 50) return 6.0;  return 0.0;
        }
        public String toString() {
            return String.format("[%d] %-18s Marks: %5.1f  Grade: %2s  Credits: %d  GPA Pts: %.1f",
                    id, name, marks, letterGrade(), credits, gpaPoints());
        }
    }

    static class Expense {
        static int counter = 1;
        int id; String description, category; double amount;
        Expense(String description, double amount, String category) {
            this.id = counter++; this.description = description;
            this.amount = amount; this.category = category;
        }
        public String toString() {
            return String.format("[%d] %-20s %-12s Rs.%.2f", id, description, category, amount);
        }
    }

    static class User {
        int id; String username, password;
        User(int id, String username, String password) {
            this.id = id; this.username = username; this.password = password;
        }
    }


    static List<User>       users    = new ArrayList<>();
    static List<Task>       tasks    = new ArrayList<>();
    static List<Note>       notes    = new ArrayList<>();
    static List<ClassEntry> schedule = new ArrayList<>();
    static List<Subject>    subjects = new ArrayList<>();
    static List<Expense>    expenses = new ArrayList<>();
    static User currentUser = null;
    static int userIdCounter = 1;
    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        seedDemoData();
        System.out.println("                                        ");
        System.out.println("        📚  STUDENT  HUB  v1.0          ");
        System.out.println("    Your All-in-One Academic Companion  ");
        System.out.println("                                        ");
        authMenu();
    }



    static void seedDemoData() {
        users.add(new User(userIdCounter++, "tanish", "1234"));
        tasks.add(new Task("Submit OOP Assignment", "Chapter 4 - Inheritance", "2025-01-15"));
        tasks.add(new Task("Study for DBMS Exam", "ER diagrams + SQL", "2025-01-18"));
        tasks.add(new Task("Complete DSA Lab", "Sorting algorithms", "2025-01-10"));
        tasks.get(2).completed = true;

        notes.add(new Note("OOP Notes", "Inheritance: child class gets parent properties.\nPolymorphism: one interface, many forms."));
        notes.add(new Note("DBMS Notes", "ACID properties: Atomicity, Consistency, Isolation, Durability."));

        schedule.add(new ClassEntry("OOP", "Monday",    "09:00", "10:00"));
        schedule.add(new ClassEntry("DBMS", "Tuesday",  "10:00", "11:00"));
        schedule.add(new ClassEntry("DSA", "Wednesday", "11:00", "12:00"));
        schedule.add(new ClassEntry("Maths", "Thursday","09:00", "10:30"));

        subjects.add(new Subject("Object Oriented Programming", 88, 4));
        subjects.add(new Subject("Data Structures & Algo",      75, 4));
        subjects.add(new Subject("Database Management",         91, 3));
        subjects.add(new Subject("Engineering Mathematics",     67, 3));

        expenses.add(new Expense("Canteen Lunch",   85,  "Food"));
        expenses.add(new Expense("Photocopy Notes", 40,  "Stationery"));
        expenses.add(new Expense("Uber to College", 120, "Transport"));
        expenses.add(new Expense("Programming Book",350, "Education"));
    }



    static void authMenu() {
        while (true) {
            System.out.println("\n");
            System.out.println("   1. Login             ");
            System.out.println("   2. Register          ");
            System.out.println("   3. Exit              ");
            System.out.println("                        ");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> login();
                case "2" -> register();
                case "3" -> { System.out.println("Goodbye! 👋"); System.exit(0); }
                default  -> System.out.println("Invalid choice.");
            }
        }
    }

    static void login() {
        System.out.print("Username: "); String u = sc.nextLine().trim();
        System.out.print("Password: "); String p = sc.nextLine().trim();
        for (User user : users) {
            if (user.username.equals(u) && user.password.equals(p)) {
                currentUser = user;
                System.out.println("\n✅ Welcome back, " + u + "!");
                showReminders();
                dashboard();
                return;
            }
        }
        System.out.println(" Invalid credentials.");
    }

    static void register() {
        System.out.print("Choose username: "); String u = sc.nextLine().trim();
        for (User user : users) {
            if (user.username.equals(u)) { System.out.println("Username taken."); return; }
        }
        System.out.print("Choose password: "); String p = sc.nextLine().trim();
        users.add(new User(userIdCounter++, u, p));
        System.out.println("Registered successfully! You can now login.");
    }

    // ─── SMART REMINDERS ─────────────────────────────────────────────────────

    static void showReminders() {
        long pending = tasks.stream().filter(t -> !t.completed).count();
        System.out.println("\n🔔 ─── Smart Reminders ──────────────────────");
        if (pending == 0) System.out.println("   ✅ No pending tasks. You're all caught up!");
        else              System.out.println("   ⚠️  You have " + pending + " pending task(s). Stay focused!");
        double gpa = calculateGPA();
        if (gpa > 0) {
            if (gpa >= 9.0)      System.out.println("   🌟 GPA " + String.format("%.2f", gpa) + " — Outstanding performance!");
            else if (gpa >= 7.0) System.out.println("   📈 GPA " + String.format("%.2f", gpa) + " — Good, keep pushing!");
            else                 System.out.println("   📉 GPA " + String.format("%.2f", gpa) + " — Needs improvement. Study harder!");
        }
        System.out.println("─────────────────────────────────────────────");
    }

    // ─── MAIN DASHBOARD ──────────────────────────────────────────────────────

    static void dashboard() {
        while (true) {
            System.out.println("\n                                   ");
            System.out.println("║          MAIN DASHBOARD            ");
            System.out.println("                                     ");
            System.out.println("   1. 📋 Task Manager                ");
            System.out.println("   2. 📝 Notes                       ");
            System.out.println("   3. 🗓  Schedule / Timetable       ");
            System.out.println("   4. 📊 GPA Calculator              ");
            System.out.println("   5. 💸 Expense Tracker             ");
            System.out.println("   6. 🤖 AI Study Advisor            ");
            System.out.println("   7. ☁️  Export / Backup Data      ");
            System.out.println("   8. 🔁 Switch User (Logout)        ");
            System.out.println("                                     ");
            System.out.print("Choice: ");
            switch (sc.nextLine().trim()) {
                case "1" -> taskMenu();
                case "2" -> noteMenu();
                case "3" -> scheduleMenu();
                case "4" -> gpaMenu();
                case "5" -> expenseMenu();
                case "6" -> aiAdvisor();
                case "7" -> exportData();
                case "8" -> { currentUser = null; return; }
                default  -> System.out.println("Invalid choice.");
            }
        }
    }


    static void taskMenu() {
        while (true) {
            System.out.println("\n── 📋 TASK MANAGER ──────────────────────");
            System.out.println("  1. View All Tasks");
            System.out.println("  2. Add Task");
            System.out.println("  3. Mark Task Complete");
            System.out.println("  4. Delete Task");
            System.out.println("  5. View Pending Only");
            System.out.println("  0. Back");
            System.out.print("Choice: ");
            switch (sc.nextLine().trim()) {
                case "1" -> listItems(tasks, "All Tasks");
                case "2" -> addTask();
                case "3" -> markTaskDone();
                case "4" -> deleteTask();
                case "5" -> {
                    System.out.println("\n── Pending Tasks ──");
                    boolean found = false;
                    for (Task t : tasks) { if (!t.completed) { System.out.println(t); found = true; } }
                    if (!found) System.out.println("No pending tasks!");
                }
                case "0" -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }

    static void addTask() {
        System.out.print("Title: ");       String title = sc.nextLine().trim();
        System.out.print("Description: "); String desc  = sc.nextLine().trim();
        System.out.print("Due Date (YYYY-MM-DD): "); String date = sc.nextLine().trim();
        if (title.isEmpty()) { System.out.println("Title is required!"); return; }
        tasks.add(new Task(title, desc, date));
        System.out.println("✅ Task added!");
    }

    static void markTaskDone() {
        listItems(tasks, "Tasks");
        System.out.print("Enter Task ID to mark done: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            for (Task t : tasks) {
                if (t.id == id) { t.completed = true; System.out.println("✅ Marked complete!"); return; }
            }
            System.out.println("Task not found.");
        } catch (NumberFormatException e) { System.out.println("Invalid ID."); }
    }

    static void deleteTask() {
        listItems(tasks, "Tasks");
        System.out.print("Enter Task ID to delete: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            tasks.removeIf(t -> t.id == id);
            System.out.println("🗑 Task deleted.");
        } catch (NumberFormatException e) { System.out.println("Invalid ID."); }
    }


    static void noteMenu() {
        while (true) {
            System.out.println("\n── 📝 NOTES ─────────────────────────────");
            System.out.println("  1. View All Notes");
            System.out.println("  2. View Note Content");
            System.out.println("  3. Add Note");
            System.out.println("  4. Delete Note");
            System.out.println("  0. Back");
            System.out.print("Choice: ");
            switch (sc.nextLine().trim()) {
                case "1" -> listItems(notes, "Notes");
                case "2" -> viewNote();
                case "3" -> addNote();
                case "4" -> {
                    listItems(notes, "Notes");
                    System.out.print("Note ID to delete: ");
                    try { int id = Integer.parseInt(sc.nextLine().trim()); notes.removeIf(n -> n.id == id); System.out.println("🗑 Deleted."); }
                    catch (NumberFormatException e) { System.out.println("Invalid."); }
                }
                case "0" -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }

    static void viewNote() {
        listItems(notes, "Notes");
        System.out.print("Enter Note ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            for (Note n : notes) {
                if (n.id == id) {
                    System.out.println("\n── " + n.title + " ──────────────");
                    System.out.println(n.content);
                    System.out.println("──────────────────────────────────");
                    return;
                }
            }
            System.out.println("Note not found.");
        } catch (NumberFormatException e) { System.out.println("Invalid ID."); }
    }

    static void addNote() {
        System.out.print("Note Title: "); String title = sc.nextLine().trim();
        if (title.isEmpty()) { System.out.println("Title required!"); return; }
        System.out.println("Enter content (type END on a new line to finish):");
        StringBuilder content = new StringBuilder();
        while (true) {
            String line = sc.nextLine();
            if (line.equals("END")) break;
            content.append(line).append("\n");
        }
        notes.add(new Note(title, content.toString().trim()));
        System.out.println("✅ Note saved!");
    }



    static void scheduleMenu() {
        while (true) {
            System.out.println("\n── 🗓 SCHEDULE / TIMETABLE ──────────────");
            System.out.println("  1. View Full Timetable");
            System.out.println("  2. View by Day");
            System.out.println("  3. Add Class");
            System.out.println("  4. Remove Class");
            System.out.println("  0. Back");
            System.out.print("Choice: ");
            switch (sc.nextLine().trim()) {
                case "1" -> listItems(schedule, "Timetable");
                case "2" -> {
                    System.out.print("Enter day (e.g. Monday): "); String day = sc.nextLine().trim();
                    boolean found = false;
                    for (ClassEntry c : schedule) {
                        if (c.day.equalsIgnoreCase(day)) { System.out.println(c); found = true; }
                    }
                    if (!found) System.out.println("No classes on " + day);
                }
                case "3" -> {
                    System.out.print("Subject: ");    String sub = sc.nextLine().trim();
                    System.out.print("Day: ");        String day = sc.nextLine().trim();
                    System.out.print("Start time: "); String st  = sc.nextLine().trim();
                    System.out.print("End time: ");   String en  = sc.nextLine().trim();
                    schedule.add(new ClassEntry(sub, day, st, en));
                    System.out.println("✅ Class added!");
                }
                case "4" -> {
                    listItems(schedule, "Schedule");
                    System.out.print("ID to remove: ");
                    try { int id = Integer.parseInt(sc.nextLine().trim()); schedule.removeIf(c -> c.id == id); System.out.println("🗑 Removed."); }
                    catch (NumberFormatException e) { System.out.println("Invalid."); }
                }
                case "0" -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }


    static void gpaMenu() {
        while (true) {
            System.out.println("\n── 📊 GPA CALCULATOR ────────────────────");
            System.out.printf( "   Current GPA: %.2f / 10.00%n", calculateGPA());
            System.out.println("  1. View All Subjects");
            System.out.println("  2. Add Subject");
            System.out.println("  3. Remove Subject");
            System.out.println("  0. Back");
            System.out.print("Choice: ");
            switch (sc.nextLine().trim()) {
                case "1" -> listItems(subjects, "Subjects");
                case "2" -> {
                    System.out.print("Subject name: "); String name = sc.nextLine().trim();
                    try {
                        System.out.print("Marks (0-100): "); double marks = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Credits: ");       int cred = Integer.parseInt(sc.nextLine().trim());
                        subjects.add(new Subject(name, marks, cred));
                        System.out.printf("✅ Added! Updated GPA: %.2f%n", calculateGPA());
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                }
                case "3" -> {
                    listItems(subjects, "Subjects");
                    System.out.print("ID to remove: ");
                    try { int id = Integer.parseInt(sc.nextLine().trim()); subjects.removeIf(s -> s.id == id); System.out.printf("🗑 Removed. GPA: %.2f%n", calculateGPA()); }
                    catch (NumberFormatException e) { System.out.println("Invalid."); }
                }
                case "0" -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }

    static double calculateGPA() {
        if (subjects.isEmpty()) return 0.0;
        double pts = 0, creds = 0;
        for (Subject s : subjects) { pts += s.gpaPoints() * s.credits; creds += s.credits; }
        return creds == 0 ? 0.0 : pts / creds;
    }



    static void expenseMenu() {
        while (true) {
            System.out.println("\n── 💸 EXPENSE TRACKER ───────────────────");
            double total = expenses.stream().mapToDouble(e -> e.amount).sum();
            System.out.printf("   Total Spent: Rs.%.2f%n", total);
            System.out.println("  1. View All Expenses");
            System.out.println("  2. Add Expense");
            System.out.println("  3. Delete Expense");
            System.out.println("  4. Split a Bill");
            System.out.println("  5. View by Category");
            System.out.println("  0. Back");
            System.out.print("Choice: ");
            switch (sc.nextLine().trim()) {
                case "1" -> listItems(expenses, "Expenses");
                case "2" -> {
                    System.out.print("Description: "); String desc = sc.nextLine().trim();
                    try {
                        System.out.print("Amount (Rs.): ");  double amt = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Category: ");      String cat = sc.nextLine().trim();
                        expenses.add(new Expense(desc, amt, cat));
                        System.out.println("✅ Expense recorded!");
                    } catch (NumberFormatException e) { System.out.println("Invalid amount."); }
                }
                case "3" -> {
                    listItems(expenses, "Expenses");
                    System.out.print("ID to delete: ");
                    try { int id = Integer.parseInt(sc.nextLine().trim()); expenses.removeIf(e -> e.id == id); System.out.println("🗑 Deleted."); }
                    catch (NumberFormatException e) { System.out.println("Invalid."); }
                }
                case "4" -> {
                    try {
                        System.out.print("Total bill amount (Rs.): "); double bill = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Number of people: "); int n = Integer.parseInt(sc.nextLine().trim());
                        System.out.printf("💡 Each person pays: Rs.%.2f%n", bill / n);
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                }
                case "5" -> {
                    System.out.print("Category: "); String cat = sc.nextLine().trim();
                    double catTotal = 0;
                    for (Expense e : expenses) {
                        if (e.category.equalsIgnoreCase(cat)) { System.out.println(e); catTotal += e.amount; }
                    }
                    System.out.printf("Total for '%s': Rs.%.2f%n", cat, catTotal);
                }
                case "0" -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }


    static void aiAdvisor() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       🤖  AI STUDY ADVISOR            ║");
        System.out.println("╚══════════════════════════════════════╝");

        long pending = tasks.stream().filter(t -> !t.completed).count();
        double gpa   = calculateGPA();
        double spent = expenses.stream().mapToDouble(e -> e.amount).sum();

        if (pending == 0) {
            System.out.println("✅ All tasks complete! Great discipline.");
        } else if (pending >= 5) {
            System.out.println("🚨 " + pending + " tasks pending — you're falling behind! Prioritize immediately.");
        } else {
            System.out.println("📋 " + pending + " task(s) pending. Stay on track!");
        }

        if (gpa == 0) {
            System.out.println("📚 Add your subjects to start tracking GPA.");
        } else if (gpa >= 9.0) {
            System.out.printf("🌟 GPA %.2f — You're in the top tier! Keep it up.%n", gpa);
        } else if (gpa >= 7.5) {
            System.out.printf("📈 GPA %.2f — Strong performance. Push for excellence!%n", gpa);
        } else if (gpa >= 6.0) {
            System.out.printf("⚠️  GPA %.2f — Average. Identify weak subjects and revise more.%n", gpa);
        } else {
            System.out.printf("📉 GPA %.2f — Critical. Please seek help from professors.%n", gpa);
        }

        if (spent > 2000) {
            System.out.printf("💸 You've spent Rs.%.0f. Consider tracking your budget more carefully.%n", spent);
        } else {
            System.out.printf("💰 Spending looks reasonable at Rs.%.0f. Keep it up!%n", spent);
        }

        subjects.stream()
                .min(Comparator.comparingDouble(s -> s.marks))
                .ifPresent(s -> System.out.println("🎯 Focus area: '" + s.name + "' (" + s.marks + " marks) needs the most attention."));

        int hours = 2 + (int)(pending / 2) + (gpa < 7.0 ? 2 : 0);
        hours = Math.min(hours, 8);
        System.out.println("⏰ Recommended study time today: " + hours + " hour(s).");
        System.out.println("📅 Study Tip: Use the Pomodoro technique — 25 min study, 5 min break.");
        System.out.println("──────────────────────────────────────────");
    }

    static void exportData() {
        System.out.println("\n── ☁️ DATA EXPORT (JSON Preview) ─────────");
        System.out.println("{");
        System.out.println("  \"user\": \"" + currentUser.username + "\",");

        System.out.println("  \"tasks\": [");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            System.out.print("    {\"id\":" + t.id + ", \"title\":\"" + t.title + "\", \"due\":\"" + t.dueDate + "\", \"done\":" + t.completed + "}");
            System.out.println(i < tasks.size() - 1 ? "," : "");
        }
        System.out.println("  ],");

        System.out.println("  \"notes\": [");
        for (int i = 0; i < notes.size(); i++) {
            Note n = notes.get(i);
            System.out.print("    {\"id\":" + n.id + ", \"title\":\"" + n.title + "\"}");
            System.out.println(i < notes.size() - 1 ? "," : "");
        }
        System.out.println("  ],");

        System.out.printf("  \"gpa\": %.2f,%n", calculateGPA());
        System.out.printf("  \"totalExpenses\": %.2f%n", expenses.stream().mapToDouble(e -> e.amount).sum());
        System.out.println("}");
        System.out.println("\n✅ (In desktop version this saves to a .json file)");
    }

    static <T> void listItems(List<T> list, String label) {
        System.out.println("\n── " + label + " (" + list.size() + " items) ──");
        if (list.isEmpty()) { System.out.println("  (none)"); return; }
        for (T item : list) System.out.println("  " + item);
    }
}
