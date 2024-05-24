import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();

        SubTask t1 = new SubTask("123", "456");
        SubTask t2 = new SubTask("122", "456");
        ArrayList<SubTask> subTasks1 = new ArrayList<>(Arrays.asList(t1, t2));
        Epic e1 = new Epic("epic1", "epic 1 desc");

        SubTask t4 = new SubTask("123", "456");
        SubTask t5 = new SubTask("123", "456");
        ArrayList<SubTask> subTasks2 = new ArrayList<>(Arrays.asList(t4, t5));
        Epic e2 = new Epic("epic2", "epic 1 desc");

        // создание эпика
        tm.addEpic(e1, subTasks1);
        tm.addEpic(e2, subTasks2);

        // создание задачи
        Task task1 = new Task("task_before", "444");
        tm.addTask(task1);

        // получить список подзадач по эпику
        // System.out.println( tm.getAllSubTasksByEpic(0) );

        // получить эпик/задачу/подзадачу по ID
        // System.out.println( tm.getTaskById(task1.getId()) );

        // вывод всех типов задач на печать
        // tm.printAll();

        // обновление статуса подзадач
        System.out.println( tm.getEpicById(e1.getId()) );
        SubTask t1_upd = new SubTask("123upd", "456");
        SubTask t2_upd = new SubTask("123upd", "456"); // проверка на уникальность
        SubTask t3_upd = new SubTask("124upd", "456");
        tm.updateSubTask(t1.getId(), t1_upd, Status.DONE);
        tm.updateSubTask(t2.getId(), t2_upd, Status.DONE);
        tm.updateSubTask(t2.getId(), t3_upd, Status.DONE);

        System.out.println( tm.getEpicById(e1.getId()) );
        // удалить все задачи
        // tm.deleteAll(Type.EPIC);

        // удалить по ID
        // tm.deleteEpicById(e1.getId());
        // tm.deleteSubTaskById(t4.getId());
        // tm.deleteTaskById(task1.getId());

        tm.printAll();
    }
}
