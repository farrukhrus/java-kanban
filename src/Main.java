import com.yandex.taskmanager.Epic;
import com.yandex.taskmanager.SubTask;
import com.yandex.taskmanager.Task;
import com.yandex.model.InMemoryTaskManager;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager tm = new InMemoryTaskManager();

        // ========== Задачи ========== //
        // создание задачи
        Task task1 = new Task("task1", "444");
        tm.addTask(task1);
        // обновление задачи
        Task task2 = new Task("task1", "555");
        task2.setId(task1.getId());
        tm.updateTask(task2);


        // ========== Эпики ========== //
        // создание эпика
        Epic e1 = new Epic("epic1", "epic1_before");
        tm.addEpic(e1);
        // обновление эпика
        Epic e2 = new Epic("epic1", "epic1_after");
        e2.setId(e1.getId());
        tm.updateEpic(e2);


        // ========== Подзадачи ========== //
        SubTask t1 = new SubTask("123", "456");
        SubTask t2 = new SubTask("123", "654");
        // добавление подзадачи в эпик
        tm.addSubTask(t1); // не задан эпик
        t2.setEpic(e2.getId());
        tm.addSubTask(t2);
        // обновление подзадачи
        SubTask t3 = new SubTask("123", "564");
        t3.setId(t2.getId());
        t3.setEpic(t2.getEpic());
        tm.updateSubTask(t3);

        // получить список подзадач по эпику
        // System.out.println( tm.getAllSubTasksByEpic(0) );

        // получить эпик/задачу/подзадачу по ID
        // System.out.println( tm.getTaskById(task1.getId()) );

        // вывод всех типов задач на печать
        tm.printAll();

        System.out.println(tm.getEpicById(e1.getId()));
        System.out.println(tm.getTaskById(task1.getId()));
        System.out.println(tm.getSubTaskById(t3.getId()));
        // удалить все задачи эпики
        tm.deleteAllEpics();
        tm.printAll();

        // удалить по ID
        tm.deleteTaskById(task2.getId());
        // tm.deleteSubTaskById(t4.getId());
        // tm.deleteTaskById(task1.getId());

        tm.printAll();
    }
}
