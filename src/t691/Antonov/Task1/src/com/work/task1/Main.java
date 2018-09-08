package com.work.task1;
import java.util.Scanner;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    static abstract class Pet{
        protected String type;
        protected String name;
        protected int health;
        protected int eat;
        Scanner in;
        Timer timer;

        public void write(String type, String name, int healt, int eat){
            try(FileWriter writer = new FileWriter("LifeTime.txt"))
            {
                writer.write(type + "\n" + name + "\n" + healt + "\n" + eat);

                writer.close();
            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }
        }

        public void read(){
            String type, name, health, eat;
            try(FileReader reader = new FileReader("LifeTime.txt")){
                Scanner in = new Scanner(reader);

                type = in.nextLine();
                name = in.nextLine();
                health = in.nextLine();
                eat = in.nextLine();

                System.out.println(type + "\n" + name + "\n" + health + "\n" + eat + "\n");

            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
                System.out.println("Файл создан с новыми данными.\n");
            }
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(eat > 0){
                    eat -= 1;
                    write(type, name, health, eat);
                }
                else{
                    System.out.println(name + " хочет кушать!");
                    health -= 1;
                    write(type, name, health, eat);
                    if(health == 0){
                        System.out.println("Питомец мертв!(");
                        write(type, name, health, eat);
                        System.exit(0);
                    }
                }
            }
        };
    }

    static class Cat extends Pet{

        Cat(String name){
            type = "Кот";
            this.name = name;
            health = 9;
            eat = 90;
            in = new Scanner(System.in);
            read();
            write(type, name, health, eat);
            timer = new Timer();
            timer.scheduleAtFixedRate(task, 1000, 2000);
        }

        Cat(String type, String name, int health, int eat){
            this.type = type;
            this.name = name;
            this.health = health;
            this.eat = eat;
            in = new Scanner(System.in);
            timer = new Timer();
            timer.scheduleAtFixedRate(task, 1000, 2000);
        }

        public void life(){
            while(health != 0){

                int temp = 0;

                System.out.println("Выберите что требуется сделать с Вашим питомцом: ");
                System.out.println("1. Кормить 2.Выход");

                temp = in.nextInt();

                switch (temp){
                    case 1: eat();
                        break;
                    case 2:
                        System.exit(0);
                    default:
                        System.out.println("Ошибка!");
                        break;
                }
            }
        }

        public void eat(){
            if(eat >= 100){
                System.out.println("Кормить больше некуда!)");
            }
            else{
                eat += 10;
                System.out.println("Ему было очень вкусно!)");
                write(type, name, health, eat);
            }
        }
    }

    static void new_game(int choose){
        String name;
        Scanner in = new Scanner(System.in);

        System.out.println("Выберите тип питомца: ");
        System.out.println("1. Кот");
        choose = in.nextInt();

        switch (choose){
            case 1:
                System.out.println("Вы выбрали кота!");
                System.out.print("Дайте имя Вашему питомцу: ");
                name = in.next();
                Cat c = new Cat(name);
                c.life();
                break;
            default:
                System.out.println("Ошибка!");
                break;
        }
    }

    static void continue_game(){

        String type, name;
        int health, eat;

        try(FileReader reader = new FileReader("LifeTime.txt")){
            Scanner in = new Scanner(reader);

            type = in.nextLine();
            name = in.nextLine();
            health = in.nextInt();
            eat = in.nextInt();

            if(health == 0){
                System.out.println("Нет живых питомцев! Начните заново!");
            }
            else {
                Cat c = new Cat(type, name, health, eat);
                c.life();
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            System.out.println("Начните новою игру.\n");
        }

    }

    public static void main(String[] args) {

        int choose = 0;
        boolean repeat = true;
        Scanner in = new Scanner(System.in);

        System.out.println("Добро пожаловать в игру! \n1. Новая игра \n2. Продолжить игру \n3. Выход из игры");
        choose = in.nextInt();

        while (repeat == true) {
            switch (choose) {
                case 1:
                    new_game(choose);
                    repeat = false;
                    break;
                case 2:
                    continue_game();
                    repeat = false;
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверно сделан выбор!");
                    break;
            }
        }

    }
}
