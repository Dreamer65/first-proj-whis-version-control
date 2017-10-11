import java.io.*;

public class LevelFile extends File {

    private byte id;

    public LevelFile(String pathname) {
        super(pathname);
    }

    public byte getId() {
        return id;
    }

    private void existsNew() throws IOException {
        if(!this.exists()){
            this.createNewFile();
        }
    }

    private boolean existsError() throws IOException {
        if(!this.exists()){
            System.out.println("Файла не существует");
            return false;
        }
        return true;
    }
    
    public void write(String text) throws IOException {
        this.existsNew();

        PrintWriter out = new PrintWriter(this.getAbsoluteFile());

        out.print(text);
        out.close();
    }

    public void update(String text) throws IOException {
        this.existsNew();
        StringBuilder stringBuilder = new StringBuilder();
        String string;
        string = read();
        stringBuilder.append(string);
        stringBuilder.append("\n");
        write(stringBuilder.toString() + text);
    }



    public String read() throws IOException {
        if(!this.existsError()){
            return null;
        }

        StringBuilder stringB = new StringBuilder();

        BufferedReader in = new BufferedReader(new FileReader(this.getAbsoluteFile()));
        String string;

        while ((string = in.readLine()) != null){
            stringB.append(string);
            stringB.append("\n");
        }

        in.close();

        return stringB.toString();
    }

    public void saveLevel(NewRoom room, byte id) throws IOException {
        NewRoom newRoom =  room;
        StringBuilder stringBuilder = new StringBuilder();
        write(String.valueOf(id) + "\n");


        do {
            stringBuilder.append(room.getQuantity());
            stringBuilder.append("\n");
            stringBuilder.append(room.getText());
            stringBuilder.append("\n\n");
            room = room.getBackDoorNext();
        }while (room != null);
        while (newRoom != null){
            for (int i = 0; i < newRoom.getQuantity(); i++){
                if(newRoom.go(i) != null){
                    stringBuilder.append(newRoom.go(i).getId());
                    stringBuilder.append(" ");
                }
                else {
                    stringBuilder.append("f");
                    stringBuilder.append(" ");
                }

            }
            stringBuilder.append("\n");
            newRoom = newRoom.getBackDoorNext();
        }


        update(stringBuilder.toString());
    }

    public NewRoom loadLevel() throws IOException {
        if(read() == null){
            System.out.println("Файл не найден или пуст. Создана новая карта");
            id = 0;
            NewRoom newRoom = new NewRoom((byte) 0, 2);
            return newRoom;
        }

        BufferedReader in = new BufferedReader(new FileReader(this.getAbsoluteFile()));

        id = Byte.parseByte(in.readLine());
        in.readLine();
        NewRoom room = new NewRoom((byte) 0, Integer.parseInt(in.readLine()));
        room.setText(in.readLine());
        in.readLine();

        for(byte i = 1; i < id; i++){
            room.createNew(i, Integer.parseInt(in.readLine()));
            room = room.getBackDoorNext();
            room.setText(in.readLine());
            in.readLine();
        }

        while (room.getBackDoorPriveous() != null){
            room = room.getBackDoorPriveous();
        }

/////////////////////////////////////
        /*
        Этот фрагмент отвечает за чтение связей
        он работает не коректно
         */
        String string;
        NewRoom newRoom = room;
        NewRoom first = room;

        for(int i = 0; i < id; i++){
            string = in.readLine();
            char stringArray;
            byte a;
            for(int j = 0; j < room.getQuantity()*2; j+=2){
                    //System.out.print(stringArray[j]);
                stringArray = string.toCharArray()[j];
                if(stringArray != 'f'){
                    a = Byte.parseByte(String.valueOf(stringArray));
                    while (newRoom.getId() != a){
                        newRoom = newRoom.getBackDoorNext();
                    }
                        room.setTonel(newRoom, j/2);
                }

            }
            room = room.getBackDoorNext();
            System.out.println();
            newRoom = first;
        }

/////////////////////////////////////////
        return first;

    }

}
