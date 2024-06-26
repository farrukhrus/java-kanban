import com.yandex.model.Epic;
import com.yandex.model.SubTask;
import com.yandex.model.Task;
import com.yandex.taskmanager.InMemoryTaskManager;

import java.util.List;

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

        Task task3 = tm.addTask(new Task("task3", "333"));
        System.out.println(task3);

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
        System.out.println( tm.getAllSubTasksByEpic(0) );

        // получить эпик/задачу/подзадачу по ID
        System.out.println(tm.getTaskById(task1.getId()));
        System.out.println(tm.getTaskById(task2.getId()));
        System.out.println(tm.getEpicById(e2.getId()));
        System.out.println(tm.getTaskById(100));

        // вывод всех типов задач на печать
        tm.printAll();

        // получить историю просмотра
        List<Task> history = tm.getHistory();
        System.out.println("\nИстория\nКол-во просмотров: " + history.size() + "\n");

        tm.getEpicById(e1.getId());
        tm.getEpicById(e2.getId());
        tm.getTaskById(task1.getId());
        tm.getSubTaskById(t3.getId());
        tm.getSubTaskById(t3.getId());
        tm.getSubTaskById(t3.getId());
        tm.getSubTaskById(t3.getId());
        tm.getSubTaskById(t3.getId());
        tm.getSubTaskById(t3.getId());

        // история просмотра не больше 10 записей
        history = tm.getHistory();
        System.out.println("\nИстория\nКол-во просмотров: " + history.size() + "\n");

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
