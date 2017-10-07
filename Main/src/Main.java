import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static NewRoom first;

    public static byte id = 0;
    public static int quantity = 2;

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        //System.out.println("Hi");

        LevelFile file = new LevelFile("test");

        //NewRoom map = new NewRoom(id++, quantity);
        NewRoom map = file.loadLevel();
        id = file.getId();
        map = goTo(map, (byte) 0);

        System.out.println(map.getId());

        first = map;
        String inputStr;
        int inputInt;

        admin: while (true){
            inputStr = in.next();
            switch (inputStr){
                case "add" : {
                    map = goTo(map, (byte) (id - 1));
                    map.createNew(id++, quantity);
                    System.out.println("Создана комната № " + map.getBackDoorNext().getId());
                    break;
                }

                case "nextBD" : {
                    if(map.getBackDoorNext() == null){
                        System.out.println("Там ничего нет");
                        break;
                    }
                    map = map.getBackDoorNext();
                    System.out.println(map.getId());
                    break;
                }

                case "priveousBD" : {
                    if(map.getBackDoorPriveous() == null){
                        System.out.println("Там ничего нет");
                        break;
                    }
                    map = map.getBackDoorPriveous();
                    System.out.println(map.getId());
                    break;
                }

                case "allRooms" : {
                    map = first;
                    System.out.println("Комната " + map.getId() + "c " + map.getQuantity() + " дверьми");
                    while (map.getId() != id - 1){
                        map = map.getBackDoorNext();
                        System.out.println("Комната " + map.getId() + "c " + map.getQuantity() + " дверьми");
                    }
                    break;
                }

                case "goto" : {
                    inputInt = in.nextInt();
                    if(inputInt < map.getId()){
                        map = first;
                    }

                    while (map.getId() != inputInt){
                        map = map.getBackDoorNext();
                    }

                    System.out.println("Вы в комнате " + map.getId());
                    break;
                }

                case "connect" : {
                    System.out.println("С чем соединить?");
                    connect(map, in.nextByte(), in.nextInt(), in.nextInt());

                    break;
                }

                case "around" : {
                    map.around();
                    break;
                }

                case "text" : {
                    map.setText(in.next());
                    break;
                }

                case "print" : {
                    System.out.println(map.getText());
                    break;
                }

                case "0" : break admin;

                default : System.out.println("Неверная команда");
            }
        }

        file.saveLevel(first, id);



        /*
        while (true){
            inputStr = in.next();
            switch (inputStr){
                case "go" : {
                    inputInt = in.nextInt();
                    if(map.tonel[inputInt] == null){
                        System.out.println("Там ничего нет");
                        break;
                    }
                    map = map.go(inputInt);
                    System.out.println("Вы в комнате " + map.getId());
                }

                case "around" : {
                    map.around();
                    break;
                }

                default : System.out.println("Неверная команда");
            }
        }

*/
        //////////////////////////////
/*
        Room room = new Room("Start", id++);
        room.printName();


        while (true) {
            String inputStr = in.next();

            switch (inputStr) {
                case "создать_спереди" : {
                    if(room.next() != null){
                        System.out.println("Там уже что-то есть...");
                        break;
                    }
                    String inputStr = in.next();
                    room.createNext(inputStr, id++);
                    room = room.next();
                    room.printName();
                    break;
                }

                case "создать_слева" : {
                    if(room.left() != null){
                        System.out.println("Там уже что-то есть...");
                        break;
                    }
                    String inputStr = in.next();
                    room.createLeft(inputStr, id++);
                    room = room.left();
                    room.printName();
                    break;
                }

                case "создать_справа" : {
                    if(room.right() != null){
                        System.out.println("Там уже что-то есть...");
                        break;
                    }
                    String inputStr = in.next();
                    room.createRight(inputStr, id++);
                    room = room.right();
                    room.printName();
                    break;
                }

                case "название" : {
                    room.printName();
                    break;
                }

                case "вперед" : {
                    if(room.next() == null){
                        System.out.println("Вы пришли в тупик");
                        break;
                    }
                    room = room.next();
                    room.printName();
                    break;
                }

                case "назад" : {
                    if(room.previous() == null){
                        System.out.println("Вы пришли в тупик");
                        break;
                    }
                    room = room.previous();
                    room.printName();
                    break;
                }

                case "на_лево" : {
                    if(room.left() == null){
                        System.out.println("Вы пришли в тупик");
                        break;
                    }
                    room = room.left();
                    room.printName();
                    break;
                }

                case "на_право" : {
                    if(room.right() == null){
                        System.out.println("Вы пришли в тупик");
                        break;
                    }
                    room = room.right();
                    room.printName();
                    break;
                }

                case "вокруг" : {
                    room.around();
                    break;
                }

                case "id" : {
                    room.getId();
                    break;
                }

                case "искать" : {
                    search(room, in.nextByte());
                    break;
                }

                case "выход" : return;

                default : System.out.println("Неизвестная команда");
            }


        }

    }

    public static void search(Room room, byte roomId){

        if(roomId >= id){
            System.out.println("Такой комнаты нет");
            return;
        }



    }
*/

    }

    public static void connect(NewRoom room, byte id, int slot1, int slot2){
        NewRoom room1 = room;
        NewRoom room2 = room;

        if(room2.getId() > id)
            room2 = first;

        while (room2.getId() != id){
            room2 = room2.getBackDoorNext();
        }

        room1.setTonel(room2, slot1);
        room2.setTonel(room1, slot2);
    }

    public static NewRoom goTo(NewRoom room, byte id){
        {
            while (room.getId() != id){
                room = room.getBackDoorPriveous();
            }

            System.out.println("Вы в комнате " + room.getId());
        }

        return room;
    }

}

