package com.yandex.taskmanager;

public class Main {

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();

        // ========== Задачи ========== //
        // создание задачи
        Task task1 = new Task("task1", "444");
        tm.addTask(task1);
        // обновление задачи
        Task task2 = new Task("task1", "555");
        tm.updateTask(task2);


        // ========== Эпики ========== //
        // создание эпика
        Epic e1 = new Epic("epic1", "epic1_before");
        tm.addEpic(e1);
        // обновление эпика
        Epic e2 = new Epic("epic1", "epic1_after");
        tm.updateEpic(e2);


        // ========== Подзадачи ========== //
        SubTask t1 = new SubTask("123", "456");
        SubTask t2 = new SubTask("123", "654");
        // добавление подзадачи в эпик
        tm.addSubTask(t1, e1);
        tm.addSubTask(t2, e1); // защита от дубликатов
        // обновление подзадачи
        t2.setEpic(t1.getEpic());
        tm.updateSubTask(t2);

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
        //tm.updateSubTask(t1.getId(), t1_upd, Status.DONE);
        //tm.updateSubTask(t2.getId(), t2_upd, Status.DONE);
        //tm.updateSubTask(t2.getId(), t3_upd, Status.DONE);

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
